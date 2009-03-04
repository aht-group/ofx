package org.openfuxml.client.gui.swt.composites;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.TableItem;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.client.gui.simple.factory.SimpleLabelFactory;
import org.openfuxml.client.gui.swt.factory.ProducerButtonFactory;
import org.openfuxml.client.gui.swt.factory.ProducerComboFactory;
import org.openfuxml.client.gui.swt.factory.ProducerEntitiesDisplayFactory;
import org.openfuxml.client.util.ImgCanvas;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxFormat;
import org.openfuxml.model.ejb.OfxProject;
import org.openfuxml.model.jaxb.ProducibleEntities;
import org.openfuxml.model.jaxb.Format.Options.Option;

/**
 * 
 * @author Andrea Frank
 */
public class ProducerComposite extends AbstractProducerComposite
{	
	static Logger logger = Logger.getLogger(ProducerComposite.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	final static int MAX_ANZ_KE = 8;

	final static String IMG_ERROR	= "/swt/images/error.gif";

	private Button btnUpdate,btnProduce;

	private TabFolder tfEntities;
	private TabItem tiAnsichtTabelle;
	private TabItem tiAnsichtMatrix;
	private ScrolledComposite scrolledCompositeMatrix;
	private Button[] checkBtnMatrix;
	private int checkBtnMatrixCounter;

	private StackLayout stackLayout;
	private Composite compositeOptionen;
	private Group[] groupsOptionen;
	
	private Button buttonDefaultOptionen;
	
	private Label lblEvent;
	private ImgCanvas imgCanvasStatus;
	private Button buttonErgebnisDetails;
	
	public ProducerComposite(Composite parent, OfxApplication ofxA, OfxProject ofxP, OfxClientControl ofxCC, Configuration config)
	{
		super(parent,SWT.NONE);
		this.ofxCC=ofxCC;
		display = this.getDisplay();
		toplevelShell = this.getShell();
		
		ofxCC.cboApplicationSelected(ofxA);
		ofxCC.cboProjectSelected(ofxP);
		
		SimpleLabelFactory slf = new SimpleLabelFactory(this,config);
		ProducerComboFactory scf = new ProducerComboFactory(this,ofxCC);
		ProducerButtonFactory sbf = new ProducerButtonFactory(this,ofxCC);
		ProducerEntitiesDisplayFactory pedf = new ProducerEntitiesDisplayFactory(ofxCC,config);
		
		GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			this.setLayout(layout);
		
		slf.createLabel("Document");
		cboDocuments = scf.createCboDocument();
		slf.createDummyLabel(1);
		btnUpdate = sbf.createBtnUpdate("update");
		
		slf.createLabel("Format");
		cboFormats = scf.createCboFormats();
		slf.createDummyLabel(1);
		btnProduce = sbf.createBtnProduce("produce");
		
		fillCboDocuments();
		fillCboFormats();
		
		tfEntities = pedf.createTabFolder(this);

		tabDiscoveredEntities = pedf.createTable(tfEntities); //new Table(tfAnsicht, SWT.CHECK);
		scrolledCompositeMatrix = pedf.createMatrix(tfEntities);
		
		tiAnsichtTabelle = new TabItem(tfEntities, SWT.NONE);
			tiAnsichtTabelle.setText("Tabellenansicht");
			tiAnsichtTabelle.setControl(tabDiscoveredEntities);
		
//		tiAnsichtMatrix = new TabItem(tfEntities, SWT.NONE);
//			tiAnsichtMatrix.setText("Matrix");
//			tiAnsichtMatrix.setControl(scrolledCompositeMatrix);	
			
		
		{
			compositeOptionen = new Composite(this, SWT.NONE);
			
			fuelleCompositeOptionen();
			
			stackLayout = new StackLayout();
			compositeOptionen.setLayout(stackLayout);			

			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.horizontalSpan = 3;
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
		
		slf.createDummyLabel(2);
		
		
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
	
	public void entitiesDiscovered()
	{
		super.entitiesDiscovered();
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
			final OfxFormat ofxF = (OfxFormat)cboFormats.getData(sFormatLabel);
			
			groupsOptionen[i] = new Group(compositeOptionen, SWT.NONE);
			groupsOptionen[i].setText(getGroupLabel(ofxF.getFormat().getOutputformat()));	
			
			RowLayout rowLayout = new RowLayout ();
			rowLayout.pack = false;
			groupsOptionen[i].setLayout (rowLayout);
			
			for(final Option o : ofxF.getFormat().getOptions().getOption())
			{
				final Button buttonOption = new Button(groupsOptionen[i], SWT.CHECK);
				buttonOption.setText(o.getName());
				buttonOption.setToolTipText(o.getDescription());
				boolean defaultSelected = new Boolean(o.getValue());
				buttonOption.setSelection(defaultSelected);
				if(defaultSelected){ofxCC.boxOptionsSelected(ofxF,o);}
				
				buttonOption.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt)
					{
						o.setValue(buttonOption.getSelection()+"");
						ofxCC.boxOptionsSelected(ofxF,o);
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


	public void comboFormateSelected()
	{
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
	 * @param isEnabled - gibt an, ob die Bedienelemente enabled bzw. disabled werden.
	 */
	public void setControlsEnabled(final boolean isEnabled)
	{
		display.asyncExec(new Runnable()
		{
			public void run()
			{
				if (!toplevelShell.isDisposed())
				{	
					cboDocuments.setEnabled(isEnabled);
					cboFormats.setEnabled(isEnabled);
					btnUpdate.setEnabled(isEnabled);
					tabDiscoveredEntities.setEnabled(isEnabled);

					btnProduce.setEnabled(isEnabled);
					buttonErgebnisDetails.setEnabled(isEnabled);
			
					compositeOptionen.setEnabled(isEnabled);
					buttonDefaultOptionen.setEnabled(isEnabled);
					
					if (isEnabled)
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
