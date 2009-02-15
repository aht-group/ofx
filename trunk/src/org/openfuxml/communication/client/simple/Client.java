package org.openfuxml.communication.client.simple;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.communication.client.dialog.HelpAboutDialog;
import org.openfuxml.producer.ejb.Application;
import org.openfuxml.producer.ejb.AvailableApplications;
import org.openfuxml.producer.ejb.AvailableFormats;
import org.openfuxml.producer.ejb.Format;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProducedEntitiesEntityFile;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.ProductionRequestEntityFile;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.producer.handler.DirectProducer;
import org.openfuxml.producer.handler.Producer;
import org.openfuxml.producer.handler.SocketProducer;
import org.openfuxml.server.DummyServer;
import org.openfuxml.util.config.factory.ClientConfFactory;

import de.kisner.util.ConfigLoader;
import de.kisner.util.LoggerInit;
import de.kisner.util.xml.XmlConfig;

/**
 * Client implementiert die Benutzeroberfläche für die FuXML-Produktion.
 * @author Andrea Frank
 */
public class Client extends Composite
{
    static Logger logger = Logger.getLogger(Client.class);
    public final static String Version = Client.class.getPackage().getImplementationVersion();
    public final static String Title = "openFuXML - Produktionssytem";

	public final static String IMG_FUXLOGO			= "/images/client/openFuXML-Logo.png";
	public final static String IMG_FUXICON			= "/images/client/openFuXML-Icon.gif";
	public final static String IMG_FUXICON_KLEIN	= "/images/client/openFuXML-Icon-klein.gif";

	public final static RGB rgbBackground = new RGB(231, 232, 235);
	
	private Menu menu;
	private Menu menuClient;
	private MenuItem menuItemClient;
	private MenuItem menuItemClientBeenden;	
	private Menu menuEinstellungen;
	private MenuItem menuItemEinstellungen;
	private MenuItem menuItemEinstellungenServer;
	private Menu menuHilfe;
	private MenuItem menuItemHilfe;
	private MenuItem menuItemHilfeInfoUeber;
	
	private Label labelVerzeichnis;
	private Button buttonWechseln;
	
	private Combo comboAnwendungen;
	private Combo comboProjekte;
	private Combo comboDokumente;
	private Combo comboFormate;

	private Button buttonAktualisieren;

	private Table tableProductionEntities;
	private Button buttonProduzieren;

	private Label labelErgebnis;
	private Button buttonOeffnen;
	
	private Shell toplevelShell;
	private Display display;
	
	private Producer producer;
	private ProducerThread producerThread;
	private int anzItems;
	
	private Hashtable<String, ProducedEntities> htProducableEntities;
	private ProducedEntities producedEntities;
	
	/**
	 * alProducedEntities speichert alle Einträge, die in dieser Sitzung produziert wurden.
	 */
	private ArrayList<String[]> alProducedEntities;
	
	private Configuration config;
	File propFile;
	
	private Cursor cursor;
	
	/**
	 * Im Konstruktor für die Klasse Client werden neben den Initialiserungen
	 * der FuXMLLogger und die Properties gesetzt. Die Oberfläche wird 
	 * generiert und ein sogenannter Splashscreen angezeigt. 
	 * @param parent
	 * @param disp
	 */
	public Client (Composite parent, Display disp, Configuration config)
	{
	    super(parent, SWT.NULL);
	    this.config=config;
		toplevelShell = (Shell)parent;
		
		this.display = disp;

		// Open the SplashScreen
		HelpAboutDialog splashscreen = new HelpAboutDialog(this.getShell(), HelpAboutDialog.SPLASH_SCREEN);
		splashscreen.open();
		
		htProducableEntities = new Hashtable<String, ProducedEntities>();
		alProducedEntities = new ArrayList<String[]>();

		logger.info("initNet");
		initNet();
		logger.info("initGUI");
		initGUI();
		
		logger.info("pause");
		// Pause für den Splashscreen einfügen
		try{Thread.sleep(3000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		
		splashscreen.close();
		splashscreen = null;
	}
	
	public void initNet()
	{	
		if(config.getString("server").equals("direct"))
		{
			logger.info("Using "+DirectProducer.class.getSimpleName());
			new DummyServer(config);
			producer = new DirectProducer(config);
		}
		else
		{
			try
			{
				logger.info("Using "+SocketProducer.class.getSimpleName());
				producer = new SocketProducer(config);
				logger.info("[OK] ProducerThread");
			}
			catch (Exception e)	{logger.fatal("Exception", e);}
		}
	}
	/**
	 * initGUI initialisiert die grafische Oberfläche.
	 */
	public void initGUI()
	{
		this.setBackground(new Color(this.getDisplay(), rgbBackground));
		
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.horizontalSpacing = 20;
			layout.verticalSpacing = 5;
			this.setLayout(layout);
		}
		
		{
			Label labelImage = new Label(this, SWT.NONE);
			labelImage.setBackground(this.getBackground());

			{
				GridData data = new GridData();
				data.widthHint = 131;
				data.heightHint = 60;
				data.horizontalSpan = 3;
				data.horizontalAlignment = GridData.END;
				data.verticalAlignment = GridData.FILL;
				labelImage.setLayoutData(data);
			}
			
			Image img = loadImage(getClass(), getDisplay(), IMG_FUXLOGO);
			if (img != null)
			{
				labelImage.setImage(img);
			}
			else
			{
				labelImage.setText("ERROR: Image not found!");
				labelImage.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));
			}
		}
		{
			Label labelVerz = new Label(this, SWT.NONE);
			labelVerz.setText("Verzeichnis");
			labelVerz.setBackground(this.getBackground());
		}
		{
			labelVerzeichnis = new Label(this, SWT.NONE);
			labelVerzeichnis.setBackground(this.getBackground());

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				labelVerzeichnis.setLayoutData(data);
			}
			
			String sDirRepo = config.getString("dirs/dir[@type='repository']");
			logger.debug("Repository: "+sDirRepo);
			File dir = new File(sDirRepo);
			if (!dir.exists())
			{
				ConfigLoader.update(("dirs/dir[@type='repository']"), System.getProperty("user.home"));
			}
			labelVerzeichnis.setText(config.getString("dirs/dir[@type='repository']"));
		}
		{
			buttonWechseln = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonWechseln.setText("   wechseln ...   ");

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				buttonWechseln.setLayoutData(data);
			}

			buttonWechseln
				.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					Einstellungen();
					}
				});
		}
		{
			Label labelAnwendungen = new Label(this, SWT.NONE);
			labelAnwendungen.setText("Anwendung");
			labelAnwendungen.setBackground(this.getBackground());
		}
		{
			comboAnwendungen = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				comboAnwendungen.setLayoutData(data);
			}
			
			comboAnwendungen.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("application", comboAnwendungen.getText());
					fuelleComboProjekte();
					fuelleComboFormate();
					
					fuelleTableProductionEntities();
					loescheErgebnis();
				}
			});
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Label labelProjekt = new Label(this, SWT.NONE);
			labelProjekt.setText("Projekt");
			labelProjekt.setBackground(this.getBackground());
		}
		{
			comboProjekte = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				comboProjekte.setLayoutData(data);
			}
			
			comboProjekte.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("project", comboProjekte.getText());
					fuelleComboDokumente();
					
					fuelleTableProductionEntities();
					loescheErgebnis();
				}
			});
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Label labelDocument = new Label(this, SWT.NONE);
			labelDocument.setText("Dokument");
			labelDocument.setBackground(this.getBackground());
		}
		{
			comboDokumente = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				comboDokumente.setLayoutData(data);
			}

			comboDokumente.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("document", comboDokumente.getText());
					fuelleTableProductionEntities();
					loescheErgebnis();
				}
			});
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Label labelFormate = new Label(this, SWT.NONE);
			labelFormate.setText("Format");
			labelFormate.setBackground(this.getBackground());
		}
		{
			comboFormate = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				comboFormate.setLayoutData(data);
			}
		
			comboFormate.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("format", comboFormate.getText());
					fuelleTableProductionEntities();
					loescheErgebnis();
				}
			});
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");

			{
				GridData data = new GridData();
				data.horizontalSpan = 2;
				labelDummy.setLayoutData(data);
			}
		}
		{
			buttonAktualisieren = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonAktualisieren.setText("   aktualisieren   ");

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				buttonAktualisieren.setLayoutData(data);
			}

			buttonAktualisieren
				.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					loescheErgebnis();
					getProducableEntities();
					}
				});
		}
		{
			tableProductionEntities = new Table(this, SWT.CHECK | SWT.BORDER);

			{
				GridData data = new GridData();
				data.widthHint = 450;
				data.heightHint = 200;
				data.horizontalSpan = 2;
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				data.verticalAlignment = GridData.FILL;
				data.grabExcessVerticalSpace = true;
				tableProductionEntities.setLayoutData(data);
			}

			{
				TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
				tableColumn.setText("");
				tableColumn.setWidth(20);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
				tableColumn.setText("Beschreibung");
				tableColumn.setWidth(160);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
				tableColumn.setText("Serverausgabe");
				tableColumn.setWidth(180);
			}
			{
				TableColumn tableColumn = new TableColumn(tableProductionEntities, SWT.NONE);
				tableColumn.setText("Dateiname");
				tableColumn.setWidth(100);
			}
			tableProductionEntities.setHeaderVisible(true);
			tableProductionEntities.setLinesVisible(true);
		}
		{
			buttonProduzieren = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonProduzieren.setText("   produzieren   ");

			{
				GridData data = new GridData();
				data.verticalAlignment = GridData.END;
				data.horizontalAlignment = GridData.FILL;
				buttonProduzieren.setLayoutData(data);
			}

			buttonProduzieren.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					loescheErgebnis();
					produce();
				}
			});
		}
		{
			labelErgebnis = new Label(this, SWT.NONE);
			labelErgebnis.setText("");
			labelErgebnis.setBackground(this.getBackground());

			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.horizontalSpan = 2;
			labelErgebnis.setLayoutData(data);
		}
		{
			buttonOeffnen = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonOeffnen.setText("   öffnen ...   ");

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				buttonOeffnen.setLayoutData(data);
			}

			buttonOeffnen.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					Oeffnen();
				}
			});
		}
		
		{
			// Inhalt der Combo Anwendung setzen
			fuelleComboAnwendungen();
		}

		// Erstellen des Menüs
		{
			menu = new Menu(getShell(), SWT.BAR);
			getShell().setMenuBar(menu);
			{
				menuItemClient = new MenuItem(menu, SWT.CASCADE);
				menuItemClient.setText("Client");
				{
					menuClient = new Menu(menuItemClient);
					{
						menuItemClientBeenden = new MenuItem(menuClient, SWT.CASCADE);
						menuItemClientBeenden.setText("Beenden");
						menuItemClientBeenden.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								ClientBeenden();
							}
							});
					}
					menuItemClient.setMenu(menuClient);
				}
			}
			{
				menuItemEinstellungen = new MenuItem(menu, SWT.CASCADE);
				menuItemEinstellungen.setText("Extras");
				{
					menuEinstellungen = new Menu(menuItemEinstellungen);
					{
						menuItemEinstellungenServer = new MenuItem(menuEinstellungen, SWT.CASCADE);
						menuItemEinstellungenServer.setText("Einstellungen ...");
						menuItemEinstellungenServer.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								Einstellungen();
							}
							});
					}
					menuItemEinstellungen.setMenu(menuEinstellungen);
				}
			}
			{
				menuItemHilfe = new MenuItem(menu, SWT.CASCADE);
				menuItemHilfe.setText("Hilfe");
				{
					menuHilfe = new Menu(menuItemHilfe);
					{
						menuItemHilfeInfoUeber = new MenuItem(menuHilfe, SWT.CASCADE);
						menuItemHilfeInfoUeber.setText("Info über ...");
						menuItemHilfeInfoUeber.addSelectionListener(new SelectionAdapter() {
							public void widgetSelected(SelectionEvent evt) {
								HilfeInfoUeber();
							}
							});
					}
					menuItemHilfe.setMenu(menuHilfe);
				}
			}
		}
	} // initGUI
	
	public void fuelleComboAnwendungen()
	{
		// Löschen der Einträge in der Combo comboAnwendungen. 
		comboAnwendungen.removeAll();

		try
		{
			this.labelErgebnis.setText("Einen Moment bitte ...");
			
			AvailableApplications availableAppliations = producer.getAvailableApplications();
			
			this.labelErgebnis.setText("");
			
			Collection<Application> collAppl = availableAppliations.getApplications();
			
			if (collAppl != null)
			{
				for(Application app : collAppl)
				{
					logger.debug("Applikation gefunden: " + app.getName());
					comboAnwendungen.add(app.getName());
				}
			}
		}
		catch (ProductionSystemException e)
		{
			logger.error("getAvailableApplications nicht möglich", e);
		}
		catch (ProductionHandlerException e)
		{
			logger.error("getAvailableApplications nicht möglich", e);

			ServerFehler();
		}		
		
		// Vorauswahl der in Properties gespeicherten Einstellungen
/*		String sAnwendung = ClientConfigWrapper.getClientConf("application");
		if (sAnwendung.length() > 0)
		{
			int index = comboAnwendungen.indexOf(sAnwendung);
			if (index != -1)
			{
				comboAnwendungen.setText(sAnwendung);
			}
		}
*/
		// Inhalt der Combos Projekte und Dokumente setzen
		fuelleComboProjekte();
		// Inhalt der Combo Formate setzen
		fuelleComboFormate();
	}

	public void fuelleComboFormate()
	{
		// Löschen der Einträge in der Combo comboFormate. 
		comboFormate.removeAll();

		

		// TODO Füllen von comboFormate bald über getAvailableFormats.
		
		try
		{
			logger.debug("Get formats for: "+comboAnwendungen.getText());
			AvailableFormats availableFormats = producer.getAvailableFormats(comboAnwendungen.getText());
			Collection collFormats = availableFormats.getFormats();

			if (collFormats != null)
			{
				Iterator it = collFormats.iterator();
				while (it.hasNext())
				{
					Format format = (Format) it.next();
					comboFormate.add(format.getFormatId());
					logger.debug("Format gefunden ("+comboAnwendungen.getText()+"): " + format.getFormatId());
				}
			}
			else
			{
				logger.error("Server meldet keine Formate! html und latexpdf wird hinzugefügt");
				comboFormate.add("html");
				comboFormate.add("latexpdf");
			}
		}
		catch (ProductionHandlerException e) {logger.error("getAvailableFormats nicht möglich", e);}
		catch (ProductionSystemException e) {logger.error(e);}		
		
		
		// Vorauswahl der in Properties gespeicherten Einstellungen
/*		String sFormat = ClientConfigWrapper.getClientConf("format");
		if (sFormat.length()>0)
		{
			int index = comboFormate.indexOf(sFormat);
			if (index != -1)
			{
				comboFormate.setText(sFormat);
			}
		}
*/	}

	/**
	 * Die Methode fuelleComboProjekte schreibt alle Verzeichnisse aus dem 
	 * Verzeichnis "labelVerzeichnis.getText()/comboAnwendungen.getText()"
	 * in die Combo comboProjekte.
	 */
	public void fuelleComboProjekte()
	{
		// Löschen der Einträge in der Combo comboProjekte. 
		comboProjekte.removeAll();

		if (comboAnwendungen.getText() != "")
		{
			String dirname = labelVerzeichnis.getText() + 
				System.getProperties().getProperty("file.separator") + 
				comboAnwendungen.getText();		

			if (!dirname.equals(""))
			{
				// Welche Unterverzeichnisse gibt es?
				File dir = new File(dirname);
				String list[] = dir.list();
				  
				if (list != null)
				{
					for (int i=0; i<list.length; i++)
					{
						File f = new File(dir, list[i]);
						if (f.isDirectory())
						{
							// Diesen Eintrag in Combo comboProjekte einfügen.
							comboProjekte.add(list[i]);
						} // if
					} // for
				} // if

				// Vorauswahl der in Properties gespeicherten Einstellungen
/*				String sProjekt = ClientConfigWrapper.getClientConf("project");
				if (sProjekt.length()>0)
				{
					int index = comboProjekte.indexOf(sProjekt);
					if (index != -1)
					{
						comboProjekte.setText(sProjekt);
					} // if
				} // if
*/			} // if
		} // if

		fuelleComboDokumente();
	}
		
	/**
	 * Die Methode fuelleComboDokumente schreibt alle Dateien aus dem Verzeichnis 
	 * "labelVerzeichnis.getText()/comboAnwendungen.getText()/comboProjekte.getText()", 
	 * die die Endung ".xml" haben, in die Combo comboDokumente.
	 */
	public void fuelleComboDokumente()
	{
		// Löschen der Einträge in der Combo comboDokumente. 
		comboDokumente.removeAll();
		// Löschen der Einträge in der Table tableProducableEntities.
		tableProductionEntities.removeAll();

		if ( (comboAnwendungen.getText()!="") && (comboProjekte.getText()!="") )
		{
			String dirname = labelVerzeichnis.getText() + File.separator + comboAnwendungen.getText();		
			
			File dir = new File(dirname, comboProjekte.getText());
			logger.debug("Suche in: "+dir.getAbsolutePath());
			String list[] = dir.list(new ExtensionFilenameFilter(".xml"));
			  
			for (int i=0; i<list.length; i++)
			{
				// Diesen Eintrag in Combo comboProjekte einfügen.
				comboDokumente.add(list[i]);
			} // for
			
			// Vorauswahl der in Properties gespeicherten Einstellungen
/*			String sDokument = ClientConfigWrapper.getClientConf("document");
			if (sDokument.length()>0)
			{
				int index = comboDokumente.indexOf(sDokument);
				if (index != -1)
				{
					comboDokumente.setText(sDokument);
				}
			} // if
*/		} // if
	}
	
	/**
	 * Die Methode fuelleTableProductionEntities füllt die Tabelle
	 * tableProductionEntities.
	 * Dabei werden anhand der ausgewählten 3 Auswahlfelder aus der Hashtable 
	 * die producableEntities in Form einer SSIMessage ermittelt.
	 * Diese werden nach den Elementen Beschreibung, Verzeichnis und Dateiname
	 * "aufgedröselt" und als neuen Eintrag in die Tabelle eingefügt.
	 */
	public void fuelleTableProductionEntities()
	{
		String s = comboProjekte.getText()+comboDokumente.getText()+comboFormate.getText();
		ProducedEntities producableEntities = (ProducedEntities) htProducableEntities.get(s);
		
		tableProductionEntities.removeAll();

		if (producableEntities != null)
		{
			for(ProducedEntitiesEntityFile ef : producableEntities.getEntityFiles())
			{
				TableItem newItem = new TableItem(tableProductionEntities, 0);
				newItem.setText(new String[] {"", ef.getDescription(),ef.getDirectory(), ef.getFilename()});
			}
		}
	}
	
	/**
	 * Nach dem Aktualisieren der ProducibleEntities werden die  
	 * producableEntities in einer Hashtable gespeichert. 
	 * Danach muss die Tabelle TableProductionEntities neu gefüllt werden. 
	 * Alle Bedienelemente werden wieder auf enabled gesetzt.  
	 */
	public void setProducedEntities(ProducedEntities pe, int err)
	{
		this.producedEntities=pe;
		final int error = err;
		
		display.asyncExec(new Runnable()
			{
				public void run()
				{
					if (!toplevelShell.isDisposed())
					{
						if (error == ProducerThread.NO_ERROR)
						{
							// Hashtable aktualisieren
							String s = comboProjekte.getText()+comboDokumente.getText()+comboFormate.getText();
							htProducableEntities.put(s, producedEntities);

							fuelleTableProductionEntities();
							setAllEnabled(true);
						}
						else
						{
							// Falls bei dem Aufruf von getProducableEntities irgendwelche Fehler auftreten,
							// kommt hier eine Fehlemeldung.
							ServerFehler();
							setAllEnabled(true);
							
							logger.fatal("ProducableEntitiesEnde: Fehlercode=" + error);
						}
					} // if
				} // run
			});
	}
	
	/**
	 * Nach dem Produzieren muss der Status der Produktion angezeigt werden.
	 * Alle Bedienelemente werden wieder auf enabled gesetzt.  
	 */
	public void setProduced(ProducedEntities pe, int err)
	{
		this.producedEntities=pe;		
		final int error = err;
		
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{
					if (error == ProducerThread.NO_ERROR)
					{
						labelErgebnis.setText("Status: " +
								"[OK] ");
						addProducedEntities(producedEntities);
						
						setAllEnabled(true);
					}
					else
					{
						// Falls bei dem Aufruf von produce irgendwelche Fehler auftreten,
						// kommt hier eine Fehlemeldung.
						labelErgebnis.setText("Status: " +
								"[ERROR] ");
						ServerFehler();
						setAllEnabled(true);
						
						logger.fatal("ProduzierenEnde: Fehlercode=" + error);
					}
				} // if
			} // run
		});
	}
	
	/**
	 * Die Methode getProducableEntities testet, ob alle Eingaben gemacht wurden
	 * und startet dann einen ProducerThread mit dem Auftrag startProducableEntities.
	 * Vor dem Start werden alle Elemente disabled, um weitere Eingaben zu vermeiden.
	 */
	public void getProducableEntities()
	{
		String Verzeichnis = labelVerzeichnis.getText();
		String Anwendung = comboAnwendungen.getText();
		String Projekt = comboProjekte.getText();
		String Dokument = comboDokumente.getText();
		String Format = comboFormate.getText();
		
		if (Verzeichnis.equals(""))
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie haben kein Verzeichnis ausgewählt."); 
			messageBox.open();
		}
		else if (Anwendung.equals(""))
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie haben keine Anwendung ausgewählt."); 
			messageBox.open();
		}
		else if (Projekt.equals(""))
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie haben kein Projekt ausgewählt."); 
			messageBox.open();
		}
		else if (Dokument.equals(""))
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie haben kein Dokument ausgewählt."); 
			messageBox.open();
		}
		else if (Format.equals(""))
		{
		    MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
		    messageBox.setText("Fehler");
		    messageBox.setMessage("Sie haben kein Format ausgewählt."); 
		    messageBox.open();
		}
		else
		{
			try
			{
				setAllEnabled(false);

				producerThread = new ProducerThread(this, producer);
				display.asyncExec(new Runnable()
					{
						public void run()
						{
							if (!toplevelShell.isDisposed())
							{
								ProductionRequest pReq = new ProductionRequest();
						    	
						    	pReq.setApplication(comboAnwendungen.getText());
								pReq.setProject(comboProjekte.getText());
								pReq.setDocument(comboDokumente.getText());
								pReq.setFormat(comboFormate.getText());
								pReq.setUsername(System.getProperty("user.name"));
								pReq.setTyp(ProductionRequest.Typ.ENTITIES);
								pReq.setSync(ProductionRequest.Sync.NOSYNC);
								producerThread.startInvoke(pReq);
							} // if
						} // run
					});
			} // try
			catch (Exception e)
			{
				// Falls bei dem Aufruf von getProducableEntities irgendwelche Fehler auftreten,
				// kommt hier eine Fehlemeldung.
				ServerFehler();
				setAllEnabled(true);
				
				logger.fatal("Exception", e);
			}
		} // else
	}

	/**
	 * Die Methode produce testet, ob Einträge der Tabelle ProductionEntities
	 * für die Produktion augewählt wurden.
	 * Danach wird der ProducerThread mit dem Auftrag starteProduktion
	 * gestartet.
	 * Vor dem Start werden alle Elemente disabled, um weitere Eingaben zu vermeiden.
	 */
	public void produce()
	{
		// Bestimmen der Anzahl der ausgewählten Einträge der Tabelle ProductionEntities.
		anzItems = 0;
		
		for(int i=0; i<tableProductionEntities.getItemCount(); i++)
		{
			TableItem tableItem = tableProductionEntities.getItem(i);
			if (tableItem.getChecked())
			{
				anzItems++;
			} // if
		} // for

		if (anzItems == 0)
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Sie  müssen mindestens einen Eintrag auswählen."); 
			messageBox.open();
		}
		else
		{
			try
			{
				setAllEnabled(false);

				producerThread = new ProducerThread(this, producer);
				display.asyncExec(new Runnable()
					{
						public void run()
						{
							if (!toplevelShell.isDisposed())
							{				
								ArrayList<ProductionRequestEntityFile> efs = new ArrayList<ProductionRequestEntityFile>();
								for(int i=0; i<tableProductionEntities.getItemCount(); i++)
								{
									TableItem tableItem = tableProductionEntities.getItem(i);
									if (tableItem.getChecked())
									{
										ProductionRequestEntityFile ef = new ProductionRequestEntityFile();
										ef.setFilename(tableItem.getText(3));
										ef.setDescription(tableItem.getText(1));
										ef.setDirectory(tableItem.getText(2));
										efs.add(ef);
									} 
								}
							
								ProductionRequest pReq = new ProductionRequest();				    	
						    	pReq.setApplication(comboAnwendungen.getText());
								pReq.setProject(comboProjekte.getText());
								pReq.setDocument(comboDokumente.getText());
								pReq.setFormat(comboFormate.getText());
								pReq.setUsername(System.getProperty("user.name"));
								pReq.setEntityFiles(efs);
								pReq.setTyp(ProductionRequest.Typ.PRODUCE);	
								pReq.setSync(ProductionRequest.Sync.NOSYNC);
								producerThread.startInvoke(pReq);
							} // if
						} // run
					});
			} // try
			catch (Exception e)
			{
				// Falls bei dem Aufruf von getProducableEntities irgendwelche Fehler auftreten,
				// kommt hier eine Fehlemeldung.
				ServerFehler();
			}
		} // else
	}
	
	/**
	 * Die Methode addProducedEntities schreibt die produzierten
	 * Einheiten (producedEntities) in die ArrayList alProducedEntities.
	 * Falls der Eintrag bereits vorhanden ist, wird er aktualisiert.
	 * @param producedEntities
	 */
	public void addProducedEntities(ProducedEntities producedEntities)
	{
		if (producedEntities != null)
		{
			for (ProducedEntitiesEntityFile ef : producedEntities.getEntityFiles())
			{
		        String sTimestamp = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM).format(new Date());
		        
				// Speichern in einem Array
		        // TODO @Thorsten: Ist es richtig den Application-Name hier vor 
		        // den Verzeichnisnamen zu setzen oder willst Du das schon in 
		        // producedEntities machen?
		        String sDir = comboAnwendungen.getText() +
		        	System.getProperties().getProperty("file.separator") + 
		        	ef.getDirectory();
		        String pe[] = {ef.getDescription(), sDir, ef.getFilename(), sTimestamp};				

				// Speichern in der ArrayList
				// Ist das Element bereits produziert worden?
				// Dann wird der Eintrag in der ArrayList aktualisiert.
				int pos = getIndexOfArrayList(pe);
				if (pos == -1)
				{
					alProducedEntities.add(pe);
				}
				else
				{
					alProducedEntities.set(pos, pe); 
				}
			} // for
		} // if
	}
	
	/**
	 * Die Methode getIndexOfArrayList liefert den Index der alProducedEntities,
	 * an der das Element, das im Prameter übergeben wird, steht.
	 * Ist das Element nicht enthalten, wird -1 geliefert.
	 * @param pe
	 * @return
	 */
	private int getIndexOfArrayList(String pe[])
	{
		for(int i=0;i<alProducedEntities.size();i++)
		{
			String s[] = (String[])alProducedEntities.get(i);
			if (s[0].equals(pe[0]) && s[1].equals(pe[1]) && s[2].equals(pe[2]))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Die Methode Oeffnen testet, ob schon ein Ergebnis vorhanden ist.
	 * Falls ja, wird der Dialog Oeffnen gestartet.
	 */
	public void Oeffnen()
	{
		if (alProducedEntities.size() > 0)
		{
			OeffnenDialog dialog = new OeffnenDialog(this.getShell(), alProducedEntities, rgbBackground,config);
			dialog.open(getShell().getImages());			
		} // if
		else
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Fehler");
			messageBox.setMessage("Es ist noch kein Ergebnis vorhanden."); 
			messageBox.open();
		} // else
	}
	
	/**
	 * Die Methode ClientBeenden schließt das Programm. 
	 */
	public void ClientBeenden()
	{
		this.getShell().close();
	}
	
	/**
	 * Die Methode Einstellungen öffnet den Dialog Einstellungen.
	 * Die neuen Einstellungen werden in myProperties gespeichert,
	 * das labelVerzeichnis wird mit der Einstellung für Repository
	 * belegt und die Combo für Projekte wird aktualisiert.
	 */
	public void Einstellungen()
	{
		EinstellungenDialog dialog = new EinstellungenDialog(this.getShell(), rgbBackground,config);
		dialog.open(getShell().getImages());

		initNet();
		
		labelVerzeichnis.setText(config.getString("dirs/dir[@type='repository']"));
		
		fuelleComboAnwendungen();
	}
	
	/**
	 * Die Methode HilfeInfoUeber öffnet den Dialog HilfeInfoUeber. 
	 */
	public void HilfeInfoUeber()
	{
		HelpAboutDialog dialog = new HelpAboutDialog(getShell(), HelpAboutDialog.ABOUT_DIALOG);
		dialog.open();
	}

	/**
	 * Die Methode ServerFehler gibt eine Fehlermeldung aus und 
	 * öffnet den Dialog Einstellungen.
	 */
	public void ServerFehler()
	{
		MessageBox d = new MessageBox(this.getShell(), SWT.ICON_ERROR| SWT.OK);
		d.setText("Fehler");
		d.setMessage("Es ist ein Fehler aufgetreten." + "\n" +
				"\n"+
				"Überprüfen Sie bitte Ihre Servereinstellungen." + "\n" +
				"\n"+
				"Host: " + config.getString("net/host") + "\n" +
				"Port: " + config.getInt("net/port")
				); 
		d.open();

		Einstellungen();
	}
	
	/**
	 * Die Methode setAllEnabled sperrt die Benutzeroberfläche für weitere Eingaben, bzw. 
	 * gibt sie wieder frei. 
	 * Sie ruft dabei für alle Bedienelemente die Methode setEnabled auf.
	 * Außerem wird der Cursor auf "Warten" bzw. auf "normal" gestellt.
	 * 
	 * @param bool - gibt an, ob die Bedienelemente enabled bzw. disabled werden.
	 */
	public void setAllEnabled(boolean bool)
	{
		menu.setEnabled(bool);

		buttonWechseln.setEnabled(bool);
		comboProjekte.setEnabled(bool);
		comboDokumente.setEnabled(bool);
		comboFormate.setEnabled(bool);
		buttonAktualisieren.setEnabled(bool);
		tableProductionEntities.setEnabled(bool);
		buttonProduzieren.setEnabled(bool);
		buttonOeffnen.setEnabled(bool);
	
		if (bool)
		{
			cursor = new Cursor(display, SWT.CURSOR_ARROW);
			toplevelShell.setCursor(cursor);
		}
		else
		{
			cursor = new Cursor(display, SWT.CURSOR_WAIT);
			toplevelShell.setCursor(cursor);
		}
	}
	
	/**
	 * Die Methode loescheErgebnis setzt das Label für den Status auf ""
	 * und productionResult auf null.
	 */
	public void loescheErgebnis()
	{
		labelErgebnis.setText("");
		producedEntities = null;
	}

	/**
	 * loadImage lädt das Image, das mittels des Resourcenamens angegeben wird.
	 * Falls die Resource nicht gefunden werden kann, liefert die Methode null.  
	 * @param cl
	 * @param display
	 * @param ResourceName
	 * @return liefert das erzeugte Image
	 */
	public final static Image loadImage(Class cl, Display display, String ResourceName)
	{
		logger.debug("getResource: " + cl.getResource(ResourceName));
		
		URL url = cl.getResource(ResourceName);
		if (url != null)
		{
			try
			{
				Image img = new Image(display, url.openStream());
				return img;
			}
			catch (Exception e)
			{
				logger.error(e.getMessage());
				return null;
			}
		}
		else
		{
			logger.debug("Resource nicht gefunden: " + ResourceName);
			return null;
		}
	}
	
	/**
	 * makeImages liefert ein Array von Images.
	 * Sollte ein Image nicht geladen werden können, ist das Ergebnisarray
	 * kleiner als das Array der Resourcedateinamen.
	 * @param Dateinamen - Array der Resourcedateinamen für die Images.
	 * @return Array der erzeugten Images
	 */
	public Image[] makeImages(String[] Dateinamen)
	{
		// Falls eine Datei für ein Image nicht existiert,
		// werden die Images erst in eine ArrayList und
		// dann in das Array Image[] für den return-Wert geschrieben.
		
		ArrayList<Image> alImages = new ArrayList<Image>();
		for (int i=0; i<Dateinamen.length; i++)
		{
			Image img = loadImage(getClass(), getDisplay(), Dateinamen[i]);
			if (img != null)
			{
				alImages.add(img);
			}
			else
			{
				logger.error("Image not found: " + Dateinamen[i]);
			}
		}
		
		Image[] img = new Image[alImages.size()];
		for (int i=0; i<alImages.size(); i++)
		{
	        img[i] = alImages.get(i);
		}
		
        return img;		
	}
	
	public static void main(String[] args)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		ClientConfFactory ccf = new ClientConfFactory();
		ccf.init("openFuXML.xml");
		
		Configuration config = ccf.getConfiguration();	
		
		Display disp = Display.getDefault();
		Shell sh = new Shell(disp);
		Client client = new Client(sh, disp, config);
		
		Point size = client.getSize();
		sh.setLayout(new FillLayout());
		sh.layout();
		if(size.x == 0 && size.y == 0)
		{
			client.pack();
			sh.pack();
		}
		else
		{
			Rectangle shellBounds = sh.computeTrim(0, 0, size.x, size.y);
			if (sh.getMenuBar() != null)
				shellBounds.height -= 22;
			sh.setSize(shellBounds.width, shellBounds.height);
		}
		sh.setText(Client.Title);

		// Icon
		final String strImages[] = {IMG_FUXICON_KLEIN, IMG_FUXICON};
		sh.setImages(client.makeImages(strImages));

		sh.pack();
		sh.open();

		while (!sh.isDisposed())
		{
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		System.exit(0);
	}
}
