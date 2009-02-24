package org.openfuxml.client.gui.swt.composites;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.client.gui.simple.factory.SimpleButtonFactory;
import org.openfuxml.client.gui.simple.factory.SimpleComboFactory;
import org.openfuxml.client.gui.simple.factory.SimpleLabelFactory;
import org.openfuxml.client.gui.simple.factory.SimpleTableFactory;
import org.openfuxml.client.gui.util.GuiSettingsValidator;
import org.openfuxml.client.util.ImgCanvas;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxDocument;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.Format.Options.Option;
import org.openfuxml.producer.exception.ProductionHandlerException;
import org.openfuxml.producer.exception.ProductionSystemException;

/**
 * 
 * @author Andrea Frank
 */
public class ProduzierenComposite extends Composite
{	
	static Logger logger = Logger.getLogger(ProduzierenComposite.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;	
	final static int MAX_ANZ_KE = 8;

	final static String IMG_ERROR	= "/swt/images/error.gif";
	
	private Display display;
	private Shell toplevelShell;
	
	private ProjektComposite projekt;	
	
	private Combo cboDocuments,cboFormats;

	private Button buttonAktualisieren;

	private TabFolder tfAnsicht;
	private TabItem tiAnsichtTabelle;
	private TabItem tiAnsichtMatrix;
	private Table tableProductionEntities;
	private Composite compositeMatrix;
	private ScrolledComposite scrolledCompositeMatrix;
	private Button[] checkBtnMatrix;
	private int checkBtnMatrixCounter;

	private StackLayout stackLayout;
	private Composite compositeOptionen;
	private Group[] groupsOptionen;
	
	private Button buttonDefaultOptionen;
	
	private Button buttonProduzieren;

	private Label lblEvent;
	private ImgCanvas imgCanvasStatus;
	private Button buttonErgebnisDetails;
		

	private ArrayList<ProductionEntity> alProductionEntities;

	
	private OfxProject ofxP;
	private OfxApplication ofxA;
	private OfxClientControl ofxCC;
	
	
	public ProduzierenComposite(Composite parent, OfxApplication ofxA, OfxProject ofxP, OfxClientControl ofxCC, ProjektComposite projekt, Configuration config)
	{
		super(parent, SWT.NONE);
		this.ofxA=ofxA;
		this.ofxP=ofxP;
		this.ofxCC=ofxCC;
		display = this.getDisplay();
		toplevelShell = this.getShell();

		this.projekt = projekt;		
		
		
		alProductionEntities = new ArrayList<ProductionEntity>();
		
		SimpleLabelFactory slf = new SimpleLabelFactory(this,config);
		SimpleComboFactory scf = new SimpleComboFactory(this,ofxCC);
		SimpleButtonFactory sbf = new SimpleButtonFactory(this,ofxCC);
		SimpleTableFactory stf = new SimpleTableFactory();
		
		{
			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			this.setLayout(layout);
		}
		
		cboDocuments = scf.createCboDocument();
		fuelleComboDokumente();
		cboFormats = scf.createCboFormats();
		fuelleComboFormate();
		
		buttonAktualisieren = sbf.createBtnUpdate();
	
		{
			tfAnsicht = new TabFolder(this, SWT.TOP);
			{
				GridData data = new GridData();
				data.grabExcessHorizontalSpace = true;
				data.grabExcessVerticalSpace = true;
				data.horizontalAlignment = GridData.FILL;
				data.verticalAlignment = GridData.FILL;
				data.horizontalSpan = 2;
				tfAnsicht.setLayoutData(data);
			}

			tableProductionEntities = stf.createTable(tfAnsicht); //new Table(tfAnsicht, SWT.CHECK);

			tiAnsichtTabelle = new TabItem(tfAnsicht, SWT.NONE);
			tiAnsichtTabelle.setText("Tabellenansicht");
			tiAnsichtTabelle.setControl(tableProductionEntities);
			
			tiAnsichtMatrix = null;
			
			{
/*
				tableProductionEntities.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
System.out.println("tablePE.widgetSelected !!!");
System.out.println("tablePE.size: " + tableProductionEntities.getItemCount());
System.out.println("alPE.size:    " + alProductionEntities.size());

						// Ein Eintrag in der Ansicht-Tabelle wurde gedrückt, daher
						//   - speichern in alProductionEntities
						//   - anzeigen der Auswahl auch in der Ansicht-Matrix
						
						// Da man hier nicht auf den Eintrag TableItem, der geklickt wurde, direkt zugreifen kann,
						// werden alle Einträge durchlaufen und evtl. identische Einträge in der Matrix-Ansicht gesetzt.
						for(int i=0; i<tableProductionEntities.getItemCount(); i++)
						{
							TableItem tableItem = tableProductionEntities.getItem(i);
							
							String s = tableItem.getText(3);

							for(int j=0; j<alProductionEntities.size(); j++)
							{
								ProductionEntity pe = (ProductionEntity) alProductionEntities.get(j);
								
								if (pe.getFilename().equals(s))
								{
									pe.setChecked(tableItem.getChecked());
									alProductionEntities.set(j, pe);
									// wenn einer gefunden wurde, aufhören
									break;
								} // if
							} // for									
						} // for

for (int i=0; i<alProductionEntities.size(); i++)
{
	ProductionEntity pe = (ProductionEntity)alProductionEntities.get(i);
	System.out.println("alPE["+i+"]:   "+pe.toString());
}
						fuelleMatrix();
						
//						speicherPEHaekchen();
 					}
				});
*/
				erzeugeAlProductionEntities();
//				setzePEHaekchen();
			}
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
		}
		{
			compositeOptionen = new Composite(this, SWT.NONE);
			
			fuelleCompositeOptionen();
			
			stackLayout = new StackLayout();
			compositeOptionen.setLayout(stackLayout);			

			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.horizontalSpan = 2;
			compositeOptionen.setLayoutData(data);

			zeigeOptionen();
//			setzeOptionenHaekchen();
		}
		{
			buttonDefaultOptionen = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonDefaultOptionen.setText("setze Default");

			GridData data = new GridData();
			data.verticalAlignment = GridData.END;
			data.horizontalAlignment = GridData.FILL;
			buttonDefaultOptionen.setLayoutData(data);

			buttonDefaultOptionen.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
//					setzeDefaultOptionen();
				}
			});
		}
		{
			Label labelDummy = new Label(this, SWT.NONE);
			labelDummy.setText("");
			
			GridData data = new GridData();
			data.horizontalSpan = 2;
			labelDummy.setLayoutData(data);
		}
		{
			buttonProduzieren = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonProduzieren.setText("produzieren");

			GridData data = new GridData();
			data.verticalAlignment = GridData.END;
			data.horizontalAlignment = GridData.FILL;
			buttonProduzieren.setLayoutData(data);

			buttonProduzieren.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					produzieren();
				}
			});
		}
		
		lblEvent = slf.creatLblEvent();
		
		{
			String resOK = config.getString("icons/@dir")+fs+config.getString("icons/icon[@type='ok']");
			imgCanvasStatus = new ImgCanvas(this, resOK);
			GridData data = new GridData();
			data.widthHint = 50;
			data.heightHint = 30;
			imgCanvasStatus.setLayoutData(data);
			imgCanvasStatus.setBackground(this.getBackground());
		}
		{
			buttonErgebnisDetails = new Button(this, SWT.PUSH | SWT.CENTER);
			buttonErgebnisDetails.setText("Details ...");

			GridData data = new GridData();
			data.verticalAlignment = GridData.END;
			data.horizontalAlignment = GridData.FILL;
			buttonErgebnisDetails.setLayoutData(data);

			buttonErgebnisDetails.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent evt) {
					AnzeigeErgebnisDetails();
				}
			});
		}
		
		pack();
		
		imgCanvasStatus.setVisible(false);
	}
	
	/**
	 * Die Methode fuelleComboDokumente schreibt alle Dateien aus dem
	 * Verzeichnis sVerzeichnis/sProjektname", die die Endung
	 * ".xml" haben, in die Combo comboDokumente.
	 */
	public void fuelleComboDokumente()
	{	// Löschen der Einträge in der Combo comboDokumente.

		cboDocuments.removeAll();
//		alMetaDokumente.clear();

//		tableProductionEntities.removeAll();
		logger.debug("!");
		logger.debug(ofxA.getName());
		logger.debug(ofxP.getName());
		List<OfxDocument> lOfxD = ofxCC.getOfxDocumentFactory().lDocuments(ofxA,ofxP);
		logger.debug(lOfxD.size());
		for (OfxDocument ofxD : lOfxD)
		{
			cboDocuments.add(ofxD.getName());
			cboDocuments.setData(ofxD.getName(),ofxD);
		}
	}
	
	/**
	 * Die Methode fuelleComboFormate schreibt alle Formate
	 * aus availableFormats in die Combo comboFormate.
	 */
	public void fuelleComboFormate()
	{
		try
		{
			List<OfxFormat> lFormats = ofxCC.getProducer().getAvailableFormats(ofxA);
			logger.debug("Get formats for: "+ofxA.getName());
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
	 * Die Methode fuelleCompositeOptionen ermittelt zu allen einstellbaren Formaten
	 * (stehen in comboFormate) die möglichen Optionen und zeigt sie in der entsprechenden
	 * Group als Buttons an.
	 * Außerdem werden die möglichen Optionen in einer Hashtable gespeichert,
	 * um sie später bei der Produktion mit den Merkmalen "displayname" und "name" 
	 * angeben zu können.
	 * Zusätzlich werden die Optionen mit ihren Default-Werten in einer Hashtable gespeichert,
	 * damit sie beim Betätigen des Buttons "setze Default"(-Optionen) auf den jeweiligen
	 * Default-Wert zurückgesetzt werden können. 
	 */
	public void fuelleCompositeOptionen()
	{
		groupsOptionen = new Group[cboFormats.getItemCount()];
		logger.debug("optionen ..."+cboFormats.getItemCount());
		for(int i=0; i<cboFormats.getItemCount(); i++)
		{
			String sFormatLabel = cboFormats.getItem(i);
			OfxFormat ofxF = (OfxFormat)cboFormats.getData(sFormatLabel);
			
			logger.debug("optionen ..."+ofxF.getFormat().getOutputformat());
			
			groupsOptionen[i] = new Group(compositeOptionen, SWT.NONE);
			groupsOptionen[i].setText(getGroupLabel(ofxF.getFormat().getOutputformat()));	
			
			RowLayout rowLayout = new RowLayout ();
			rowLayout.pack = false;
			groupsOptionen[i].setLayout (rowLayout);
			
			for(Option o : ofxF.getFormat().getOptions().getOption())
			{
				Button buttonOption = new Button(groupsOptionen[i], SWT.CHECK);
				buttonOption.setText(o.getName());
				buttonOption.setToolTipText(o.getDescription());
				
				buttonOption.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
//						speicherOptionenHaekchen();					
					}
				});	
			}			
		} // for
	} // fuelleCompositeOptionen

	/**
	 * Die Optionen sind vom eingestellten Format (z. Zt. html, latexpdf, validation) 
	 * abhängig. Je nachdem welches Format gewählt wurde, sind andere Optionen
	 * möglich. Die verschiedenen Optionen sind zu Gruppen zusammengefaßt. 
	 * Die Methode zeigeOptionen ermittelt, welche Gruppe angezeigt wird. 
	 * Im Stacklayout wird diese Gruppe dann angezeigt.
	 */
	public void zeigeOptionen()
	{
		int i = cboFormats.getSelectionIndex();
		if (i > -1)
		{
			logger.debug("Zeige optionen "+i);
			stackLayout.topControl = groupsOptionen[i];
			compositeOptionen.layout();
		}
	}
	
	/**
	 * TODO
	 *
	 */
	public void erzeugeAlProductionEntities()
	{
		alProductionEntities.clear();
/*
		if(!comboDokumente.getText().equals("") && !comboFormate.getText().equals("")) 
		{
			try	
			{
				System.out.println("ID: "+projekt.getProjectValue().getID() +" Dokument: "+ getDokFilenameFromSelection()+" Format: " + JobValue.getFormatInt(comboFormate.getText()));				
				producableEntities=myProjectUi.getPE(getDokIdFromSelection(), JobValue.getFormatInt(comboFormate.getText()));
				System.out.println("PeCache gefunden");
			}
			catch (RemoteException e){e.printStackTrace();}
		}
		else {producableEntities=null;}
			
		if (producableEntities != null)
		{
String hString = producableEntities.getMessageString();
System.out.println("producableEntities-Message-String:");
System.out.println(hString);

			// Umwandeln der benötigten Informationen und schreiben in die ArrayList alProductionEntities.

			// ??? Diese werden später an das aufrufende Element (Parent) übergeben.
			// producableEntities in Document umwandeln
			Document doc = producableEntities.getMessage();
			// Alle Nodes mit dem Namen "file" raussuchen.
			NodeList nl_file = doc.getElementsByTagName("file");
			// Gefundene Knoten zählen.
			int anzPE = nl_file.getLength();
			
			// Für jeden gefundenen Knoten die Attribute "filename" und 
			// "directory" sowie den Wert für "description" in der ArrayList 
			// alProductionEntities speichern.
			for (int i=0; i<anzPE; i++)
			{
				Node n_file = nl_file.item(i);
				
		        NodeList nl_desc 	= ((Element)n_file).getElementsByTagName("description");
		        String sDesc = "";
		        // Falls keine Description angegeben ist, wird die Description auf "" leer gesetzt.
		        try
		        {
		        	sDesc		= nl_desc.item(0).getFirstChild().getNodeValue();
		        }
		        catch (NullPointerException e)
		        {
		        	sDesc = "";
		        }

	        	String sDir			= "";				
				String sFilename	= "";
		        try
		        {
		        	sDir		= n_file.getAttributes().getNamedItem("directory").getNodeValue();				
					sFilename	= n_file.getAttributes().getNamedItem("filename").getNodeValue();
		        }
		        catch (NullPointerException e)
		        {
		        	System.out.println("Fehler: directory bzw. filename in den ProducableEntities nicht angegeben.");
		        	System.out.println("producableEntities-Message-String:");
//		        	System.out.println(producableEntities.getMessageString());
		        }
		        
				ProductionEntity pe = new ProductionEntity(false, sDesc, sDir, sFilename);
				alProductionEntities.add(pe);		        
			} // for
		} // if
		
		fuelleTableProductionEntities();
*/		fuelleMatrix();
	} // erzeugeAlProductionEntities
	
	/**
	 * TODO neu formulieren
	 * Anhand der beiden ausgewählten Auswahlfelder (Dokument und Format)
	 * werden aus einer Hashtable die ProducableEntities, die schon einmal
	 * heruntergeladen wurden, ermittelt. 
	 * Das Ergebnis wird in die Tabelle der ProducableEntities  
	 * (tableProductionEntities) geschrieben. Sollte es leer sein, bleibt 
	 * auch die Tabelle leer und der Anwender muss erst auf aktualisieren 
	 * gehen.
	 */
	public void fuelleTableProductionEntities()
	{
		tableProductionEntities.removeAll();
		
		for (int i=0; i<alProductionEntities.size(); i++)
		{
			TableItem newItem = new TableItem(tableProductionEntities, 0);
			
			ProductionEntity pe = (ProductionEntity)alProductionEntities.get(i);
			String sDir			= pe.getDirectory();
			String sFilename	= pe.getFilename();
	        String sDesc		= pe.getDescription();

	        newItem.setText(new String[] {"", sDesc, sDir, sFilename});
        	newItem.setChecked(pe.getChecked());
		} // for
	} // fuelleTableProductionEntities
			
	public void fuelleMatrix()
	{
		// Bearbeiten der "Matrixansicht"
		if (!cboFormats.getText().equals("latexpdf"))
		{
			TabItem ti[] = {tiAnsichtTabelle};
			tfAnsicht.setSelection(ti);
			if (tiAnsichtMatrix != null)
			{
				tiAnsichtMatrix.dispose();
				tiAnsichtMatrix = null;
			}
		}
		else
		{
			// "latexpdf" ist ausgewählt
			if (tiAnsichtMatrix == null)
			{
				// Nur wenn kein TabItem tiAnsichtMatrix da ist,
				// wird ein neues erstellt.
				tiAnsichtMatrix = new TabItem(tfAnsicht, SWT.NONE);
				tiAnsichtMatrix.setText("Matrixansicht");
			}
			
			{
				if (scrolledCompositeMatrix != null)
				{
					scrolledCompositeMatrix.dispose();
				}
				
				scrolledCompositeMatrix = new ScrolledComposite(tfAnsicht, SWT.H_SCROLL | SWT.V_SCROLL);
				{
					GridData data = new GridData();
					data.grabExcessHorizontalSpace = true;
					data.grabExcessVerticalSpace = true;
					data.horizontalAlignment = GridData.FILL;
					data.verticalAlignment = GridData.FILL;
					data.horizontalSpan = 1;
					scrolledCompositeMatrix.setLayoutData(data);
				}

				compositeMatrix = new Composite(scrolledCompositeMatrix, SWT.NONE);
				{
					{
						GridLayout layout = new GridLayout();
						layout.numColumns = 5;
						layout.marginHeight = 20;
						layout.marginWidth = 20;
						layout.horizontalSpacing = 20;
						layout.verticalSpacing = 20;
						//layout.makeColumnsEqualWidth = true;
						compositeMatrix.setLayout(layout);
					}
					{
						Label l;
						l = new Label(compositeMatrix, SWT.NONE);
						l.setText("Kurseinheit");
						l = new Label(compositeMatrix, SWT.NONE);
						l.setText("Lehrtext");
						l = new Label(compositeMatrix, SWT.NONE);
						l.setText("Einsendeaufgaben");
						l = new Label(compositeMatrix, SWT.NONE);
						l.setText("Musterlösungen");
						l = new Label(compositeMatrix, SWT.NONE);
						l.setText("Korrekturversion");
					}
					
					// TODO config.xml: Das Füllen der Matrix ist bisher ziemlich statisch ...
					// und soll in Zukunft über die config.xml erweitert werden.
					
					{
						// maximal MAX_ANZ_KE Kurseinheiten
						// maximal 4 Elemente pro Kurseinheit
						// => maximal 4*MAX_ANZ_KE Checkboxen
						checkBtnMatrix = new Button[4*MAX_ANZ_KE];
						
						checkBtnMatrixCounter = 0;
						for (int i=1; i<=MAX_ANZ_KE; i++)
						{
							Label l;
							l = new Label(compositeMatrix, SWT.NONE);
							l.setText(""+i);

							// Ist ein Eintrag ke"i".pdf im Array filename enthalten?
							boolean gefunden = false;
							for (int j=0; j<alProductionEntities.size(); j++)
							{
								String sFilename = "ke"+i+".pdf";
								String sALFilename = ((ProductionEntity)(alProductionEntities.get(j))).getFilename();
								if (sALFilename.equals(sFilename))
								{
									checkBtnMatrix[checkBtnMatrixCounter] = new Button(compositeMatrix, SWT.CHECK);
									checkBtnMatrix[checkBtnMatrixCounter].setToolTipText(sFilename);
									boolean bool = ((ProductionEntity)(alProductionEntities.get(j))).getChecked();
									checkBtnMatrix[checkBtnMatrixCounter].setSelection(bool);
									checkBtnMatrixCounter++;
									gefunden = true;
									break;
								}
							} // for
							if (!gefunden)
							{
								Label dummyLabel = new Label(compositeMatrix, SWT.NONE);
								dummyLabel.setText("/");
							}
							
							// Ist ein Eintrag ea"i".pdf im Array filename enthlanten?
							gefunden = false;
							for (int j=0; j<alProductionEntities.size(); j++)
							{
								String sFilename = "ea"+i+".pdf";
								String sALFilename = ((ProductionEntity)(alProductionEntities.get(j))).getFilename();
								if (sALFilename.equals(sFilename))
								{
									checkBtnMatrix[checkBtnMatrixCounter] = new Button(compositeMatrix, SWT.CHECK);
									checkBtnMatrix[checkBtnMatrixCounter].setToolTipText(sFilename);
									boolean bool = ((ProductionEntity)(alProductionEntities.get(j))).getChecked();
									checkBtnMatrix[checkBtnMatrixCounter].setSelection(bool);
									checkBtnMatrixCounter++;
									gefunden = true;
									break;
								}
							} // for
							if (!gefunden)
							{
								Label dummyLabel = new Label(compositeMatrix, SWT.NONE);
								dummyLabel.setText("/");
							}
							
							// Ist ein Eintrag ml"i".pdf im Array filename enthlanten?
							gefunden = false;
							for (int j=0; j<alProductionEntities.size(); j++)
							{
								String sFilename = "ml"+i+".pdf";
								String sALFilename = ((ProductionEntity)(alProductionEntities.get(j))).getFilename();
								if (sALFilename.equals(sFilename))
								{
									checkBtnMatrix[checkBtnMatrixCounter] = new Button(compositeMatrix, SWT.CHECK);
									checkBtnMatrix[checkBtnMatrixCounter].setToolTipText(sFilename);
									boolean bool = ((ProductionEntity)(alProductionEntities.get(j))).getChecked();
									checkBtnMatrix[checkBtnMatrixCounter].setSelection(bool);
									checkBtnMatrixCounter++;
									gefunden = true;
									break;
								}
							} // for
							if (!gefunden)
							{
								Label dummyLabel = new Label(compositeMatrix, SWT.NONE);
								dummyLabel.setText("/");
							}

							// Ist ein Eintrag korr"i".pdf im Array filename enthlanten?
							gefunden = false;
							for (int j=0; j<alProductionEntities.size(); j++)
							{
								String sFilename = "kor"+i+".pdf";
								String sALFilename = ((ProductionEntity)(alProductionEntities.get(j))).getFilename();
								if (sALFilename.equals(sFilename))
								{
									checkBtnMatrix[checkBtnMatrixCounter] = new Button(compositeMatrix, SWT.CHECK);
									checkBtnMatrix[checkBtnMatrixCounter].setToolTipText(sFilename);
									boolean bool = ((ProductionEntity)(alProductionEntities.get(j))).getChecked();
									checkBtnMatrix[checkBtnMatrixCounter].setSelection(bool);
									checkBtnMatrixCounter++;
									gefunden = true;
									break;
								}
							} // for
							if (!gefunden)
							{
								Label dummyLabel = new Label(compositeMatrix, SWT.NONE);
								dummyLabel.setText("/");
							}

						} // for (i=1..8)

						// Allen Buttons SelectionListener zusweisen.  
						for (int i=0; i<checkBtnMatrixCounter; i++)
						{
							checkBtnMatrix[i].addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									
									// Ein Button in der Ansicht-Matrix wurde gedrückt, daher
									//   - speichern in alProductionEntities
									//   - anzeigen der Auswahl auch in tableProductionEntities
									
									Object source = evt.getSource();
									String s = ((Button)source).getToolTipText();

									for(int j=0; j<alProductionEntities.size(); j++)
									{
										ProductionEntity pe = (ProductionEntity) alProductionEntities.get(j);
										
										if (pe.getFilename().equals(s))
										{
											pe.setChecked(((Button)source).getSelection());
											alProductionEntities.set(j, pe);
											// wenn einer gefunden wurde, aufhören
											break;
										} // if
									} // for									
									
									fuelleTableProductionEntities();
									
//									speicherPEHaekchen();
								}
							});
						} // for							
					}
				}
				
				scrolledCompositeMatrix.setContent(compositeMatrix);

				Point pt = compositeMatrix.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				scrolledCompositeMatrix.setExpandHorizontal(true);
				scrolledCompositeMatrix.setExpandVertical(true);
				scrolledCompositeMatrix.setMinWidth(pt.x);
				scrolledCompositeMatrix.setMinHeight(pt.y);
				
				scrolledCompositeMatrix.getVerticalBar().setIncrement(10);
				scrolledCompositeMatrix.getVerticalBar().setPageIncrement(10);
				scrolledCompositeMatrix.getHorizontalBar().setIncrement(10);
				scrolledCompositeMatrix.getHorizontalBar().setPageIncrement(10);
				
				tiAnsichtMatrix.setControl(scrolledCompositeMatrix);
			}
		}
	} // fuelleMatrix

	/**
	 * Die Methode setzePEHaekchen wird die in den ProjectUserSettings gespeicherten
	 * Häkchen auf die ArrayList alProductionEntitites übertragen.
	 * Anschließend wird die Anzeige akualisiert (fuelleTableProductionEntities, fuelleMatrix).
	 */
/*	public void setzePEHaekchen()
	{
		ArrayList<ProductionEntity> al = projekt.getMyProjectUserSettings().getAlProductionEntities();

		for (int i=0; i<al.size(); i++)
		{
			ProductionEntity pe1 = (ProductionEntity)al.get(i);
			
			for(int j=0; j<alProductionEntities.size(); j++)
			{
				ProductionEntity pe2 = (ProductionEntity)alProductionEntities.get(j);
				
				if (	pe1.getDescription().equals(pe2.getDescription()) && 
						pe1.getDirectory().equals(pe2.getDirectory()) && 
						pe1.getFilename().equals(pe2.getFilename()) )
				{
					pe2.setChecked(pe1.getChecked());
					alProductionEntities.set(j, pe2);
				} // if
			} // for
		} // for		
		
		fuelleTableProductionEntities();
		fuelleMatrix();
	} // setzePEHaekchen
*/
	/**
	 * Die Methode speicherPEHaekchen speichert die  ArrayList alProductionEntities,
	 * in der auch die aktuelle Auswahl gespeichert ist, in den ProjectUserSettings.
	 */
/*	public void speicherPEHaekchen()
	{
		// Liste der Häkchen löschen
		getProjektComposite().getMyProjectUserSettings().clearAlProductionEntities();
		
		// Speichern der aktuellen ArrayList AlProductionEntities
		ArrayList<ProductionEntity> al = new ArrayList<ProductionEntity>(alProductionEntities);
		getProjektComposite().getMyProjectUserSettings().setAlProductionEntities(al);
	} // speicherPEHaekchen
*/
	/**
	 * Die Methode setzeOptionenHaekchen wird die in den ProjectUserSettings gespeicherten
	 * Häkchen für die Optionen wieder anzeigen.
	 */
/*	public void setzeOptionenHaekchen()
	{
		ArrayList al = projekt.getMyProjectUserSettings().getAlOptionen();

		// Erst alle Häkchen auf "false" setzen.
		for (int k=0; k<groupsOptionen.length; k++)
		{
			Control control[] = groupsOptionen[k].getChildren();
			for(int j=0; j<control.length; j++)
			{
				((Button) control[j]).setSelection(false);
			} // for
		} // for
		
		// Dann die "true"-Häkchen bestimmen.
		for (int i=0; i<al.size(); i++)
		{
			String s[] = (String[])al.get(i);
			for (int k=0; k<groupsOptionen.length; k++)
			{
				Control control[] = groupsOptionen[k].getChildren();
				for(int j=0; j<control.length; j++)
				{
					if (groupsOptionen[k].getText().equals(getGroupLabel(s[0])) &&
						((Button) control[j]).getText().equals(s[1]))
					{
						((Button) control[j]).setSelection(true);
					}
				} // for
			} // for
		} // for
	} // setzeOptionenHaekchen
*/
/*	public void speicherOptionenHaekchen()
	{
		// Bestimmen der ausgewählten Optionen
		ArrayList<String[]> alOptionen = new ArrayList<String[]>();
		for (int i=0; i<groupsOptionen.length; i++)
		{
			Control control[] = groupsOptionen[i].getChildren();
			for (int j=0; j<control.length; j++)
			{
				if ( ((Button) control[j]).getSelection() )
				{
					//String sFormat = comboFormate.getItem(i);
					String sDisplayname = ((Button) control[j]).getText();
					//ProducerOption aProducerOption = (ProducerOption) htOptionen.get(ProducerOption.getKey(sFormat, sDisplayname));
					//String sName = aProducerOption.getName(); 
					
					String[] s = {comboFormate.getItem(i), sDisplayname};
					alOptionen.add( s );
				}
			}
		}
		
		// setzen in ProjectUserSettings
		projekt.getMyProjectUserSettings().setAlOptionen(alOptionen);			
	}
*/	
	/**
	 * Die Methode setzeDefaultOptionen setzt die Default-Werte der Optionen zu
	 * dem aktuell ausgewaehlten Format.
	 */
/*	public void setzeDefaultOptionen()
	{
		int index = comboFormate.getSelectionIndex();
		String sFormat = comboFormate.getText(); 
		
		Control control[] = groupsOptionen[index].getChildren();
		for (int j=0; j<control.length; j++)
		{
			String sKey = ProducerOption.getKey(sFormat, ((Button)control[j]).getText());
			ProducerOption aProducerOption = (ProducerOption) htOptionen.get(sKey);
			((Button)control[j]).setSelection(aProducerOption.getValue());
		}
		
		speicherOptionenHaekchen();
	}
*/
	public void comboDokumenteSelected()
	{
		

//		projekt.getMyProjectUserSettings().clearAlSelected();
		erzeugeAlProductionEntities();
		// TODO überprüfen der folgenden Behauptung
		// Die TPE wurde neu gefüllt, es sind keine Einträge ausgewählt,
		// also brauchen auch keine Einträge in den ProjectUserSettings 
		// gespeichert zu werden.
	}

	public void comboFormateSelected()
	{
		
		
//		projekt.getMyProjectUserSettings().clearAlSelected();
		erzeugeAlProductionEntities();
		// TODO überprüfen der folgenden Behauptung
		// Die TPE wurde neu gefüllt, es sind keine Einträge ausgewählt,
		// also brauchen auch keine Einträge in den ProjectUserSettings 
		// gespeichert zu werden.

		zeigeOptionen();
	}

	public void AnzeigeErgebnisDetails()
	{
/*		if (productionResult != null)
		{
			MessageBox d = new MessageBox(this.getShell(), SWT.ICON_INFORMATION | SWT.OK);
			d.setText("Details");
			d.setMessage(productionResult.getMessageString()); 
			d.open();		
		}
*/	}
	
	
	/**
	 * Die Methode getProducableEntities testet, ob alle Eingaben gemacht wurden
	 * und startet dann einen ProducerThread mit dem Auftrag startProducableEntities.
	 * Vor dem Start werden alle Elemente disabled, um weitere Eingaben zu vermeiden.
	 */
	public void getProducableEntities()
	{			
		try
		{
			GuiSettingsValidator.checkSet(cboDocuments);
			GuiSettingsValidator.checkSet(cboFormats);
			display.asyncExec(new Runnable()
				{
					public void run()
					{
						if (!toplevelShell.isDisposed())
						{
							OfxDocument ofxD = (OfxDocument)cboDocuments.getData(cboDocuments.getText());
							OfxFormat ofxF = (OfxFormat)cboFormats.getData(cboFormats.getText());
							
							ofxCC.getProducibleEntities(ofxA,ofxP,ofxD,ofxF);
						}
					}
				});			
		}
		catch (IllegalArgumentException e)
		{
			MessageBox messageBox = new MessageBox(this.getShell(), SWT.ICON_ERROR | SWT.OK);
			messageBox.setText("Error");
			messageBox.setMessage(e.getMessage()); 
			messageBox.open();
		}
	}
	
	
	public void produzieren()
	{
		logger.debug("aktualisieren");
	}


	/**
	 * Die Methode setAllEnabled sperrt das ProduzierenComposite für 
	 * weitere Eingaben, bzw. gibt es wieder frei. 
	 * Sie ruft dabei für alle Bedienelemente die Methode setEnabled auf.
	 * Außerem wird der Cursor auf "Warten" bzw. auf "normal" gestellt.
	 * 
	 * @param bool - gibt an, ob die Bedienelemente enabled bzw. disabled werden.
	 */
	public void setAllEnabled(boolean bool)
	{
		cboDocuments.setEnabled(bool);
		cboFormats.setEnabled(bool);
		buttonAktualisieren.setEnabled(bool);
		tableProductionEntities.setEnabled(bool);
		if (compositeMatrix!=null)
		{
			compositeMatrix.setEnabled(bool);
		}
		buttonProduzieren.setEnabled(bool);
		buttonErgebnisDetails.setEnabled(bool);

		compositeOptionen.setEnabled(bool);
		buttonDefaultOptionen.setEnabled(bool);
		
		if (bool)
		{
			Cursor cursor = new Cursor(display, SWT.CURSOR_ARROW);
			setCursor(cursor);
		}
		else
		{
			Cursor cursor = new Cursor(display, SWT.CURSOR_WAIT);
			setCursor(cursor);
		}
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
	
	public ProjektComposite getProjektComposite()
	{
		return projekt;
	}
	
	/**
	 * Die Methode getGroupLabel gibt einen String zurück, der Group als Text übergeben wird.
	 * Die Methode wurde erstellt, damit keine Tipp-Fehler oder spätere Änderungen zu Fehlern führen. 
	 * @param s
	 * @return
	 */
	public String getGroupLabel(String s)
	{
		return("Optionen für das Format \""+s+"\"");
	}
}
