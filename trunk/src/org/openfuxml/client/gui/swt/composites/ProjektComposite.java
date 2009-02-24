package org.openfuxml.client.gui.swt.composites;

import java.io.FileNotFoundException;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.openfuxml.client.control.ClientGuiCallback;
import org.openfuxml.client.control.OfxClientControl;
import org.openfuxml.client.gui.swt.OpenFuxmlClient;
import org.openfuxml.client.gui.swt.SwtGuiCallback;
import org.openfuxml.model.ejb.OfxApplication;
import org.openfuxml.model.ejb.OfxProject;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

/**
 * @author andy
 * @author Thorsten Kisner
 */
public class ProjektComposite extends Composite
{	
	static Logger logger = Logger.getLogger(ProjektComposite.class);
	
	private OpenFuxmlClient client;
	private TabFolder tabFolder;
	
	private ProduzierenComposite pComp;
	/*	private OeffnenComposite oComp; 
	private LogComposite logComp;
	private BenutzerComposite benutzerComp;
	private DocComposite docComp;
	private EinstellungenComposite einstComp;
*/	

/*	private DispatcherHome hDispatcher;
	private ProjectUi myProjectUi;
	private UserUi myUserUi;
	private ProjectValue myProjectValue;
*/	
	private Configuration config;
	private OfxApplication ofxA;
	private OfxProject ofxP;
	private OfxClientControl ofxCC;
	
	public ProjektComposite(Composite parent, OpenFuxmlClient client, OfxProject ofxP, Configuration config)
	{
		super(parent, SWT.NONE);
		this.client = client;
		this.ofxP=ofxP;

		this.config=config;
	
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent evt) {
				//TODO Settings speichern
			}
		});
		
		ofxCC = new OfxClientControl(config,new SwtGuiCallback());
		
		ofxA = new OfxApplication();
		ofxA.setName("fuxml");
		initGUI();
	}
	
	private void initGUI()
	{
        GridLayout layout = new GridLayout();
			layout.marginHeight = 10;
			layout.marginWidth = 10;
			layout.verticalSpacing = 10;
			this.setLayout(layout);
		
/*		Label label = new Label(this, SWT.NONE);
			label.setText("Projektname: " + ofxP.getName());		
			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			label.setLayoutData(data);
*/
		tabFolder = new TabFolder(this, SWT.TOP);
			tabFolder.setSelection(0);
			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			tabFolder.setLayoutData(data);
			
		addTabProduce();
		
		//TODO Migration Process (tk, 2009-02-17)
		boolean d = false;
		if(d)
		{
			addTabOpen();
			addTabLog();
			addTabUser();
			addTabDoc();
			addTabSettings();
		}
		ClientGuiCallback guiCallback = new SwtGuiCallback(pComp);
		ofxCC.setGuiCallback(guiCallback);
	}
	
	private void addTabProduce()
	{
		TabItem tiProduzieren = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='produce']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiProduzieren.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiProduzieren.setText("Produzieren");
			pComp = new ProduzierenComposite(tabFolder, ofxA, ofxP, ofxCC, this,config);
			tiProduzieren.setControl(pComp);
	}
	
	private void addTabOpen()
	{
		TabItem tiOeffnen = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='open']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiOeffnen.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiOeffnen.setText("Öffnen");
//			oComp = new OeffnenComposite(tabFolder, this);
//			tiOeffnen.setControl(oComp);
	}
	
	private void addTabLog()
	{		
		TabItem tiLogView = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='log']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiLogView.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiLogView.setText("Log");
//			logComp = new LogComposite(tabFolder, this, iniCtx,myProjectUi);
//			tiLogView.setControl(logComp);
	}
	
	private void addTabUser()
	{
		TabItem tiBenutzer = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='user']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiBenutzer.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiBenutzer.setText("Benutzer");
//			benutzerComp = new BenutzerComposite(tabFolder, this,myProjectUi);
//			tiBenutzer.setControl(benutzerComp);
	}
	
	private void addTabDoc()
	{
		TabItem tiDoc = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='documents']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiDoc.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiDoc.setText("Dokumente");
//			docComp = new DocComposite(tabFolder, this);
//			tiDoc.setControl(docComp);
	}
	
	private void addTabSettings()
	{
		TabItem tiEinst = new TabItem(tabFolder, SWT.NONE);
		String res = config.getString("icons/@dir")+"/"+config.getString("icons/project/icon[@type='preferences']");
		try
		{
			Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), res, getDisplay());
			tiEinst.setImage(img);
		}
		catch (FileNotFoundException e) {logger.error(e);}
		tiEinst.setText("Einstellungen");
//			einstComp = new EinstellungenComposite(tabFolder, this);
//			tiEinst.setControl(einstComp);
	}
	
	public OpenFuxmlClient getClient(){return client;}
/*	public ProjectValue getProjectValue(){return myProjectValue;}
	public OeffnenComposite getOeffnen(){return oComp;}
	public LogComposite getLogComp(){return logComp;}
	public ProduzierenComposite getPComp(){return pComp;}
	public ProjectUi getMyProjectUi(){return myProjectUi;}
	public ProjectUserSettings getMyProjectUserSettings(){return myProjectUserSettings;}
*/		
}
