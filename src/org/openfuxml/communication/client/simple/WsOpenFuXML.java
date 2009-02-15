package org.openfuxml.communication.client.simple;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jdom.Attribute;
import org.jdom.Element;
import org.openfuxml.util.config.factory.ClientConfFactory;

import de.kisner.util.LoggerInit;
import de.kisner.util.architecture.ArchUtil;
import de.kisner.util.io.ObjectIO;
import de.kisner.util.io.resourceloader.MultiResourceLoader;
import de.kisner.util.xml.XmlConfig;
import de.kisner.util.xml.XmlElementNotFoundException;

public class WsOpenFuXML
{
	static Logger logger = Logger.getLogger(WsOpenFuXML.class);
	private static String fs = SystemUtils.FILE_SEPARATOR;
	
	String version,openFuXMLDir;
	static String fSep;
	
	public WsOpenFuXML()
	{
		fSep=SystemUtils.FILE_SEPARATOR;
	}
	
	public void loadProperties()
	{
		try
		{
			openFuXMLDir = ArchUtil.getAppSettingsDir("openFuXML");
			version = this.getClass().getPackage().getImplementationVersion();
			if(version==null)
			{
				String propName = "resources/properties/ant.properties.dist";
				logger.info("No package Version available. Using "+propName);
				InputStream is = MultiResourceLoader.searchIs(this.getClass().getClassLoader(), propName);
				Properties versionProperties = new Properties();
				versionProperties.load(is);
				version = versionProperties.getProperty("openfuxml-version");
			}
			logger.info("Version: "+version);
		}
		catch (IOException e){logger.fatal("IOException", e);}
	}
	
	public void checkExtract()
	{
		File baseDir = new File(openFuXMLDir);
		File appDir = new File(openFuXMLDir+fSep+version);
		
		if(!baseDir.exists())
		{
			logger.info("Create directory: "+baseDir.getAbsolutePath());
			baseDir.mkdir();
		}
		
		if(appDir.exists() && appDir.isDirectory())
		{
			logger.debug("Verzeichnis existiert bereits");
		}
		else
		{
			createDirs(appDir);
			extract("dist","openFuXML-app.zip",baseDir.getAbsolutePath(),appDir);
			extract("dist","openFuXML-lib.zip",baseDir.getAbsolutePath(),appDir);
			extract("dist","openFuXML-conf.zip",baseDir.getAbsolutePath(),appDir);
		}
	}
	
	private void createDirs(File appDir)
	{
		appDir.mkdir();
		File logDir = new File(appDir.getAbsoluteFile()+fSep+"logs");
		logDir.mkdir();
	}
	
	private void extract(String zipDir, String zipFileName,String baseDir,File appDir)
	{
		try
		{
			ClassLoader cl = this.getClass().getClassLoader();
			InputStream is = MultiResourceLoader.searchIs(cl, zipDir+fSep+zipFileName);
			if(is!=null)
			{
				logger.debug(is.getClass().getSimpleName()+" available: "+is.available());
				
				File zipFile = new File(baseDir+fSep+zipFileName);
				ObjectIO.writeToFile(baseDir+fSep+zipFileName, is);
				boolean result = ObjectIO.extractZip(zipFile, appDir);
				logger.debug("Neue Version ("+version+") wird angelegt. Success:"+result);
				zipFile.deleteOnExit();
			}
		}
		catch (IOException e){logger.error(e);}
	}
	
	public void initGui()
	{
		//TODO diesen Krempel prüfen
		String xml = openFuXMLDir+fSep+version+fSep+"openFuXML-config.xml";
		String xsd = openFuXMLDir+fSep+version+fSep+"openFuXML-1.x.xsd";
		logger.warn("No validation will be done with "+xml);
		XmlConfig xCnf = new XmlConfig(xml,true);//, xsd);
		
		String baseDir;
		try {baseDir = xCnf.getTextException("dirs/dir[@type=\"basedir\"]");}
		catch (XmlElementNotFoundException e)
		{
			baseDir = xCnf.getWorkingDir();
			logger.warn("No \"baseDir\" defined in xmlConfig. Using WorkingDir: "+baseDir);
		}
		
		Element xmlBaseDir = new Element("dir");
		xmlBaseDir.setAttribute(new Attribute("typ","basedir")); 
		xmlBaseDir.setText(openFuXMLDir+fSep+version);
		try{xCnf.updateElement("dirs/dir[@type=\"basedir\"]", xmlBaseDir);}
		catch (XmlElementNotFoundException e) {e.printStackTrace();}
		
		ClientConfFactory ccf = new ClientConfFactory();
		ccf.init("openFuXML.xml");
		Configuration config = ccf.getConfiguration();	
		
		Display disp = Display.getDefault();
		Shell sh = new Shell(disp);
		Client client = new Client(sh, disp,config);
		
		Point size = client.getSize();
		sh.setLayout(new FillLayout());
		sh.layout();
		if(size.x == 0 && size.y == 0)
		{
			client.pack();
			sh.pack();
		} else {
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

		while (!sh.isDisposed()) {
			if (!disp.readAndDispatch())
				disp.sleep();
		}
		System.exit(0);
	}
	
	public static void main(String[] args)
	{		
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WsOpenFuXML openFuXML = new WsOpenFuXML();
		openFuXML.loadProperties();
		openFuXML.checkExtract();
		openFuXML.initGui();
	}
}
