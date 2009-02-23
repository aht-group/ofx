package org.openfuxml.client.simple;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.openfuxml.client.simple.factory.SimpleButtonFactory;
import org.openfuxml.client.simple.factory.SimpleComboFactory;
import org.openfuxml.client.simple.factory.SimpleLabelFactory;
import org.openfuxml.client.simple.factory.SimpleMenuFactory;
import org.openfuxml.client.simple.factory.SimpleTableFactory;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Productionresult;
import org.openfuxml.model.jaxb.Sessionpreferences.Productionentities;
import org.openfuxml.producer.ejb.ProducedEntities;
import org.openfuxml.producer.ejb.ProducedEntitiesEntityFile;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;
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
	
	private Label lblRepository,lblEvent;
	private Button btnChange,btnUpdate,btnProduce;
	
	private Combo cboApplications,cboProjects,cboDocuments,cboFormats;

	private Table tableProductionEntities;
	
	private Shell toplevelShell;
	private Display display;
	
	
	/**
	 * alProducedEntities speichert alle Einträge, die in dieser Sitzung produziert wurden.
	 */
	private ArrayList<String[]> alProducedEntities;
	
	private Configuration config;
	private OpenFuxmlClientControl ofxCC;
	
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
	
		alProducedEntities = new ArrayList<String[]>();

		logger.info("initGUI");
		guiInit();
		
		logger.info("pause");

		try{Thread.sleep(1000);} catch (InterruptedException e){logger.error("InterruptedException", e);}
		
		splashscreen.close();
		splashscreen = null;
	}
	
	/**
	 * initGUI initialisiert die grafische Oberfläche.
	 */
	public void guiInit()
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
		
		SimpleLabelFactory slf = new SimpleLabelFactory(this,config);
		
		slf.createLogo();
		logger.debug("Logo");
		SimpleButtonFactory sbf = new SimpleButtonFactory(this);
		SimpleComboFactory scf = new SimpleComboFactory(this,config);
		lblRepository = scf.createCboRepository();
		
		btnChange = sbf.createBtnChange();

		cboApplications = scf.createCboApplication();
		cboProjects = scf.createCboProject();
		cboDocuments = scf.createCboDocument();
		cboFormats = scf.createCboFormats();
		
		btnUpdate = sbf.createBtnUpdate();
		
		tableProductionEntities = SimpleTableFactory.createTable(this);
		tableProductionEntities.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent evt) {
				// Bestimmen des ausgewählten Eintrags.
				TableItem[] selection = tableProductionEntities.getSelection();
				TableItem selectedRow = selection[0];

				logger.debug(selectedRow.getText(1)+" "+selectedRow.getText(2));
			}
		});
		
		btnProduce = sbf.createBtnProduce();
		
		{
			lblEvent = new Label(this, SWT.NONE);
			lblEvent.setText("");
			lblEvent.setBackground(this.getBackground());

			GridData data = new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.horizontalSpan = 2;
			lblEvent.setLayoutData(data);
		}
		
		fillCboApplications();

		menu = SimpleMenuFactory.createMenu(getShell(),this);
	}
	
	public void fillCboApplications()
	{
		cboApplications.removeAll();
		try
		{
			this.lblEvent.setText("Einen Moment bitte ...");
			
			List<OfxApplication> lOfxA = ofxCC.getProducer().getAvailableApplications();
			this.lblEvent.setText("");
			
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
			}
*/		}
	}
	
	/**
	 * Die Methode fuelleTableProductionEntities füllt die Tabelle
	 * tableProductionEntities.
	 * Dabei werden anhand der ausgewählten 3 Auswahlfelder aus der Hashtable 
	 * die producableEntities in Form einer SSIMessage ermittelt.
	 * Diese werden nach den Elementen Beschreibung, Verzeichnis und Dateiname
	 * "aufgedröselt" und als neuen Eintrag in die Tabelle eingefügt.
	 */
	public void entitiesDiscovered()
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{
					tableProductionEntities.removeAll();
					OfxApplication ofxA = (OfxApplication)cboApplications.getData(cboApplications.getText());
					OfxProject ofxP = (OfxProject)cboProjects.getData(cboProjects.getText());
					OfxDocument ofxD = (OfxDocument)cboDocuments.getData(cboDocuments.getText());
					OfxFormat ofxF = (OfxFormat)cboFormats.getData(cboFormats.getText());
					ProducibleEntities pe = ofxCC.getCachedProducibleEntities(ofxA, ofxP, ofxD, ofxF);
					if (pe != null)
					{
						for(ProducibleEntities.File f :pe.getFile())
						{
							TableItem newItem = new TableItem(tableProductionEntities, 0);
							newItem.setText(new String[] {"", f.getDescription(),f.getDirectory(), f.getFilename()});
						}
					};
					setAllEnabled(true);
				}
			}
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
					lblEvent.setText(status);
				}
			}
		});
	}
	
	/**
	 * Nach dem Produzieren werden alle Bedienelemente wieder auf enabled gesetzt.  
	 */
	public void entitiesProduced()
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed()){setAllEnabled(true);}
			}
		});
	}

	private void checkSet(Combo cbo) throws IllegalArgumentException
	{
		if(cbo.getText().equals(""))
		{
			throw new IllegalArgumentException("You have to chose a "+cbo.getData());
		}
	}
	
	/**
	 * Die Methode getProducableEntities testet, ob alle Eingaben gemacht wurden
	 * und startet dann einen ProducerThread mit dem Auftrag startProducableEntities.
	 * Vor dem Start werden alle Elemente disabled, um weitere Eingaben zu vermeiden.
	 */
	public void getProducableEntities()
	{			
		try
		{
			checkSet(cboApplications);
			checkSet(cboProjects);
			checkSet(cboDocuments);
			checkSet(cboFormats);
			if(lblRepository.getText().equals("")){throw new IllegalArgumentException("You have to chose a repository!");}
			
			try
			{
				setAllEnabled(false);
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
								
								ofxCC.getProducibleEntities(ofxA,ofxP,ofxD,ofxF);
							}
						}
					});
			}
			catch (Exception e)
			{
				// Falls bei dem Aufruf von getProducableEntities irgendwelche Fehler auftreten,
				// kommt hier eine Fehlemeldung.
				ServerFehler();
				setAllEnabled(true);
				
				logger.fatal("Exception", e);
			}
			
		}
		catch (IllegalArgumentException e)
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Error");
			messageBox.setMessage(e.getMessage()); 
			messageBox.open();
		}
	}

	/**
	 * Die Methode produce testet, ob Einträge der Tabelle ProductionEntities
	 * für die Produktion augewählt wurden.
	 * Danach wird der ProducerThread mit dem Auftrag starteProduktion
	 * gestartet.
	 * Vor dem Start werden alle Elemente disabled, um weitere Eingaben zu vermeiden.
	 */
	public void produce()
	{	// Bestimmen der Anzahl der ausgewählten Einträge der Tabelle ProductionEntities.
		int anzItems = 0;
		
		for(int i=0; i<tableProductionEntities.getItemCount(); i++)
		{
			TableItem tableItem = tableProductionEntities.getItem(i);
			if (tableItem.getChecked()){anzItems++;}
		}

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
	public void addProducedEntities1(ProducedEntities producedEntities)
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
		lblRepository.setText(labelText);
		
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

		btnChange.setEnabled(bool);
		cboProjects.setEnabled(bool);
		cboDocuments.setEnabled(bool);
		cboFormats.setEnabled(bool);
		btnUpdate.setEnabled(bool);
		tableProductionEntities.setEnabled(bool);
		btnProduce.setEnabled(bool);
	
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
		lblEvent.setText("");
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
			try
			{
				Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), Dateinamen[i], getDisplay());
				alImages.add(img);
			}
			catch (FileNotFoundException e){logger.warn(e);}
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