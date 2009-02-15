package org.openfuxml.client.composites;

import java.io.File;
import java.io.InputStream;
import java.rmi.RemoteException;

import javax.ejb.RemoveException;
import javax.naming.InitialContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.openfuxml.client.OpenFuxmlClient;
import org.openfuxml.model.ejb.OfxProject;

import de.kisner.util.io.resourceloader.ImageResourceLoader;

/**
 * @author andy
 */
public class ProjektComposite extends Composite
{
	final static String IMG_PRODUCE		= "/swt/images/tab/produce.png";
	final static String IMG_OPEN		= "/swt/images/tab/open.gif";
	final static String IMG_LOG			= "/swt/images/tab/log.png";
	final static String IMG_USERS		= "/swt/images/tab/users.gif";
	final static String IMG_DOCUMENTS	= "/swt/images/tab/documents.png";
	final static String IMG_PREFERENCES	= "/swt/images/tab/preferences.png";	
	
	private OpenFuxmlClient client;
	private OfxProject ofxProject;
	private TabFolder tabFolder;
	
/*	private ProduzierenComposite pComp;
	private OeffnenComposite oComp; 
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
	public ProjektComposite(Composite parent, OpenFuxmlClient client, OfxProject ofxProject)
	{
		super(parent, SWT.NONE);
		this.client = client;
		this.ofxProject=ofxProject;
	
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent evt) {
				//TODO Settings speichern
				
			}
		});

		initGUI();
	}
	
	private void initGUI()
	{
        GridLayout layout = new GridLayout();
			layout.marginHeight = 20;
			layout.marginWidth = 20;
			layout.verticalSpacing = 20;
			this.setLayout(layout);
		
		Label label = new Label(this, SWT.NONE);
			label.setText("Projektname: " + ofxProject.getName());		
			GridData data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			label.setLayoutData(data);

		tabFolder = new TabFolder(this, SWT.TOP);
			tabFolder.setSelection(0);
			data = new GridData();
			data.grabExcessHorizontalSpace = true;
			data.grabExcessVerticalSpace = true;
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.FILL;
			tabFolder.setLayoutData(data);
			
		addTabProduce();
		boolean d = false;
		if(d)
		{
			addTabOpen();
			addTabLog();
			addTabUser();
			addTabDoc();
			addTabSettings();
		}
	}
	
	private void addTabProduce()
	{
		TabItem tiProduzieren = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_PRODUCE, getDisplay());
		tiProduzieren.setImage(img);
		tiProduzieren.setText("Produzieren");
//			pComp = new ProduzierenComposite(tabFolder, this, hDispatcher,myProjectUi);
//			tiProduzieren.setControl(pComp);
	}
	
	private void addTabOpen()
	{
		TabItem tiOeffnen = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_OPEN, getDisplay());
		tiOeffnen.setImage(img);
		tiOeffnen.setText("Öffnen");
//			oComp = new OeffnenComposite(tabFolder, this);
//			tiOeffnen.setControl(oComp);
	}
	
	private void addTabLog()
	{		
		TabItem tiLogView = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_LOG, getDisplay());
		tiLogView.setImage(img);
		tiLogView.setText("Log");
//			logComp = new LogComposite(tabFolder, this, iniCtx,myProjectUi);
//			tiLogView.setControl(logComp);
	}
	
	private void addTabUser()
	{
		TabItem tiBenutzer = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_USERS, getDisplay());
		tiBenutzer.setImage(img);
		tiBenutzer.setText("Benutzer");
//			benutzerComp = new BenutzerComposite(tabFolder, this,myProjectUi);
//			tiBenutzer.setControl(benutzerComp);
	}
	
	private void addTabDoc()
	{
		TabItem tiDoc = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_DOCUMENTS, getDisplay());
		tiDoc.setImage(img);
		tiDoc.setText("Dokumente");
//			docComp = new DocComposite(tabFolder, this);
//			tiDoc.setControl(docComp);
	}
	
	private void addTabSettings()
	{
		TabItem tiEinst = new TabItem(tabFolder, SWT.NONE);
		Image img = ImageResourceLoader.search(this.getClass().getClassLoader(), IMG_PREFERENCES, getDisplay());
		tiEinst.setImage(img);
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
