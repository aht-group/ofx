package org.openfuxml.client.simple;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.control.OpenFuxmlClientControl;
import org.openfuxml.client.simple.dialog.HelpAboutDialog;
import org.openfuxml.client.simple.factory.SimpleMenuFactory;
import org.openfuxml.client.simple.factory.SimpleTableFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences.Productionentities;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProducedEntitiesEntityFile;
import org.openfuxml.producer.ejb.ProductionRequest;
import org.openfuxml.producer.ejb.ProductionRequestEntityFile;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
import org.openfuxml.util.config.OfxPathHelper;
import org.openfuxml.util.config.factory.ClientConfFactory;

import de.kisner.util.LoggerInit;
import de.kisner.util.io.resourceloader.ImageResourceLoader;

/**
 * Client implementiert die Benutzeroberfläche für die FuXML-Produktion.
 * @author Andrea Frank
 */
public class Client extends Composite implements ClientGuiCallback
{
    static Logger logger = Logger.getLogger(Client.class);
    private static String fs = SystemUtils.FILE_SEPARATOR;
    
    public final static String Version = Client.class.getPackage().getImplementationVersion();
    public final static String Title = "openFuXML - Produktionssytem";

	public final static RGB rgbBackground = new RGB(231, 232, 235);
	
	private Menu menu;
	
	private Label labelVerzeichnis;
	private Button buttonWechseln;
	
	private Combo cboApplications,cboProjects,cboDocuments,cboFormats;

	private Button buttonAktualisieren;

	private Table tableProductionEntities;
	private Button buttonProduzieren;

	private Label labelErgebnis;
	private Button buttonOeffnen;
	
	private Shell toplevelShell;
	private Display display;
	
	private ProducerThread producerThread;
	private int anzItems;
	
	private Hashtable<String, ProducedEntities> htProducableEntities;
	private ProducedEntities producedEntities;
	
	/**
	 * alProducedEntities speichert alle Einträge, die in dieser Sitzung produziert wurden.
	 */
	private ArrayList<String[]> alProducedEntities;
	
	private Configuration config;
	private OpenFuxmlClientControl ofxCC;
	private Productionresult presult;
	
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
		
		ofxCC = new OpenFuxmlClientControl(config,this);
		
		HelpAboutDialog splashscreen = new HelpAboutDialog(this.getShell(), HelpAboutDialog.SPLASH_SCREEN,config);
		splashscreen.open();
		
		htProducableEntities = new Hashtable<String, ProducedEntities>();
		alProducedEntities = new ArrayList<String[]>();

		logger.info("initGUI");
		initGUI();
		
		logger.info("pause");

		try{Thread.sleep(1000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		
		splashscreen.close();
		splashscreen = null;
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
			String res = config.getString("logos/@dir")+fs+config.getString("logos/logo[@type='fuxklein']");
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
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
			
			labelVerzeichnis.setText(OfxPathHelper.getDir(config, "repository"));
			logger.debug("Repository: "+labelVerzeichnis.getText());
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
			cboApplications = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				cboApplications.setLayoutData(data);
			}
			
			cboApplications.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("application", comboAnwendungen.getText());
					fillCboProjects();
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
			cboProjects = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				cboProjects.setLayoutData(data);
			}
			
			cboProjects.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					//TODO Update Client Settings
					//ClientConfigWrapper.updateKeyValue("project", comboProjekte.getText());
					fillCboDocuments();
					
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
			cboDocuments = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				cboDocuments.setLayoutData(data);
			}

			cboDocuments.addSelectionListener(new SelectionAdapter() {
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
			cboFormats = new Combo(this, SWT.READ_ONLY | SWT.NONE);

			{
				GridData data = new GridData();
				data.horizontalAlignment = GridData.FILL;
				data.grabExcessHorizontalSpace = true;
				cboFormats.setLayoutData(data);
			}
		
			cboFormats.addSelectionListener(new SelectionAdapter() {
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
		
		tableProductionEntities = SimpleTableFactory.createTable(this);
		
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
		
		fillCboApplications();

		menu = SimpleMenuFactory.createMenu(getShell(),this);
	}
	
	public void fillCboApplications()
	{
		cboApplications.removeAll();
		try
		{
			this.labelErgebnis.setText("Einen Moment bitte ...");
			
			List<OfxApplication> lOfxA = ofxCC.getProducer().getAvailableApplications();
			this.labelErgebnis.setText("");
			
			if (lOfxA != null)
			{
				for(OfxApplication ofxA : lOfxA)
				{
					cboApplications.add(ofxA.getName());
					cboApplications.setData(ofxA.getName(),ofxA);
				}
			}
		}
		catch (ProductionSystemException e) {logger.error("getAvailableApplications nicht möglich", e);}
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
	}

	public void fuelleComboFormate()
	{
		cboFormats.removeAll();
		try
		{
			List<OfxFormat> lFormats = ofxCC.getProducer().getAvailableFormats((OfxApplication)cboApplications.getData(cboApplications.getText()));
			logger.debug("Get formats for: "+cboApplications.getText());
			if(lFormats!=null && lFormats.size()>0)
			{
				for(OfxFormat ofxF : lFormats)
				{
					cboFormats.add(ofxF.getFormat().getTitle());
					cboFormats.setData(ofxF.getFormat().getTitle(),ofxF);
				}
			}
			else {logger.error("Server meldet keine Formate!");}
		}
		catch (ProductionSystemException e) {logger.error(e);}
		catch (ProductionHandlerException e) {logger.error(e);}
	}

	/**
	 * Die Methode fuelleComboProjekte schreibt alle Verzeichnisse aus dem 
	 * Verzeichnis "labelVerzeichnis.getText()/comboAnwendungen.getText()"
	 * in die Combo comboProjekte.
	 */
	public void fillCboProjects()
	{
		cboProjects.removeAll();
		logger.debug("Search Projects ...");
		if (cboApplications.getText().length()>0)
		{
			List<OfxProject> lOfxP = ofxCC.getOfxProjectFactory().lProjects(cboApplications.getText());
			for(OfxProject ofxP : lOfxP)
			{
				cboProjects.add(ofxP.getName());
				cboProjects.setData(ofxP.getName(),ofxP);
				logger.debug(ofxP);
			}
		}
	}
		
	/**
	 * Die Methode fuelleComboDokumente schreibt alle Dateien aus dem Verzeichnis 
	 * "labelVerzeichnis.getText()/comboAnwendungen.getText()/comboProjekte.getText()", 
	 * die die Endung ".xml" haben, in die Combo comboDokumente.
	 */
	public void fillCboDocuments()
	{
		cboDocuments.removeAll();
		tableProductionEntities.removeAll();
		
		
		if ( (cboApplications.getText().length()>0) && (cboProjects.getText().length()>0) )
		{
			
			OfxApplication ofxA = (OfxApplication)cboApplications.getData(cboApplications.getText());
			OfxProject ofxP = (OfxProject)cboProjects.getData(cboProjects.getText());

			List<OfxDocument> lOfxD = ofxCC.getOfxDocumentFactory().lDocuments(ofxA,ofxP);
			
			for (OfxDocument ofxD : lOfxD)
			{
				cboDocuments.add(ofxD.getName());
				cboDocuments.setData(ofxD.getName(),ofxD);
			}
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
		String s = cboProjects.getText()+cboDocuments.getText()+cboFormats.getText();
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
							String s = cboProjects.getText()+cboDocuments.getText()+cboFormats.getText();
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
	
	public void setStatus(final String status)
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{
					labelErgebnis.setText(status);
				}
			}
		});
	}
	
	/**
	 * Nach dem Produzieren muss der Status der Produktion angezeigt werden.
	 * Alle Bedienelemente werden wieder auf enabled gesetzt.  
	 */
	public void setProduced(final Productionresult presult)
	{
		this.presult=presult;
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{
					addProducedEntities(presult);
					setAllEnabled(true);
				}
			}
		});
	}
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
						labelErgebnis.setText("Status: " +"[OK] ");
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
		String Anwendung = cboApplications.getText();
		String Projekt = cboProjects.getText();
		String Dokument = cboDocuments.getText();
		String Format = cboFormats.getText();
		
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

				producerThread = new ProducerThread(this, ofxCC.getProducer());
				display.asyncExec(new Runnable()
					{
						public void run()
						{
							if (!toplevelShell.isDisposed())
							{
								
								
								OfxApplication ofxA = (OfxApplication)cboApplications.getData(cboApplications.getText());
								OfxProject ofxP = (OfxProject)cboProjects.getData(cboProjects.getText());
								OfxDocument ofxD = (OfxDocument)cboDocuments.getData(cboDocuments.getText());
								OfxFormat ofxF = (OfxFormat)cboFormats.getData(cboFormats.getText());
								
								ofxCC.getProducibleEntities(ofxA,ofxP,ofxD);
								
								ProductionRequest pReq = new ProductionRequest();
						    	
						    	pReq.setApplication(cboApplications.getText());
								pReq.setProject(cboProjects.getText());
								pReq.setDocument(cboDocuments.getText());
								pReq.setFormat(ofxF.getFormat().getId());
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
			setAllEnabled(false);

			producerThread = new ProducerThread(this, ofxCC.getProducer());
			display.asyncExec(new Runnable()
				{
					public void run()
					{
						if (!toplevelShell.isDisposed())
						{				
							Productionentities pe = new Productionentities();
							for(int i=0; i<tableProductionEntities.getItemCount(); i++)
							{
								TableItem tableItem = tableProductionEntities.getItem(i);
								if (tableItem.getChecked())
								{
									Productionentities.File f = new Productionentities.File();
										f.setDescription(tableItem.getText(1));
										f.setDirectory(tableItem.getText(2));
										f.setFilename(tableItem.getText(3));
									pe.getFile().add(f);
								} 
							}
							
							OfxApplication ofxA = (OfxApplication)cboApplications.getData(cboApplications.getText());
							OfxProject ofxP = (OfxProject)cboProjects.getData(cboProjects.getText());
							OfxDocument ofxD = (OfxDocument)cboDocuments.getData(cboDocuments.getText());
							OfxFormat ofxF = (OfxFormat)cboFormats.getData(cboFormats.getText());
						
							ofxCC.produce(ofxA, ofxP, ofxD, ofxF, pe);
							
						} 
					}
				});
		} // else
	}
	
	/**
	 * Die Methode addProducedEntities schreibt die produzierten
	 * Einheiten (producedEntities) in die ArrayList alProducedEntities.
	 * Falls der Eintrag bereits vorhanden ist, wird er aktualisiert.
	 * @param producedEntities
	 */
	public void addProducedEntities(Productionresult presult)
	{
		
	}
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
		        String sDir = cboApplications.getText() +
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

		ofxCC = new OpenFuxmlClientControl(config,this);
		
		String labelText=config.getString("dirs/dir[@type='repository']");;
		if(config.getBoolean("dirs/dir[@type='repository']/@rel"))
		{
			labelText=config.getString("dirs/dir[@type='basedir']")+fs+labelText;
		}
		labelVerzeichnis.setText(labelText);
		
		fillCboApplications();
	}
	
	/**
	 * Die Methode HilfeInfoUeber öffnet den Dialog HilfeInfoUeber. 
	 */
	public void HilfeInfoUeber()
	{
		HelpAboutDialog dialog = new HelpAboutDialog(getShell(), HelpAboutDialog.ABOUT_DIALOG,config);
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
		cboProjects.setEnabled(bool);
		cboDocuments.setEnabled(bool);
		cboFormats.setEnabled(bool);
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
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), Dateinamen[i], getDisplay());
			if (img != null){alImages.add(img);}
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

		String resIconFuxKlein = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fuxklein']");
		String resIconFux = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='fux']");
		final String strImages[] = {resIconFuxKlein, resIconFux};
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