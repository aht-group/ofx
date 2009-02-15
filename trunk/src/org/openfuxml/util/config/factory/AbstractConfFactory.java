package org.openfuxml.util.config.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;

import de.kisner.util.architecture.ArchUtil;
import de.kisner.util.io.resourceloader.MultiResourceLoader;

public abstract class AbstractConfFactory
{
	static Logger logger = Logger.getLogger(AbstractConfFactory.class);
	protected static String fs = SystemUtils.FILE_SEPARATOR;
	protected static enum StartUpEnv {DEVELOPER,PRODUCTION}
	protected StartUpEnv startupenv;

	public String openFuxmlVersion;
	public File openFuxmlBaseDir;
	
	public AbstractConfFactory()
	{
		getVersion();
		getBaseDir();
	}
	
	private void getBaseDir()
	{
		switch(startupenv)
		{
			case DEVELOPER:		openFuxmlBaseDir = new File(".");break;
			case PRODUCTION:	openFuxmlBaseDir = new File(ArchUtil.getAppSettingsDir("openFuXML"));
								if(!openFuxmlBaseDir.exists())
								{
									logger.warn("Application directory does not exit: "+openFuxmlBaseDir.getAbsolutePath());
									logger.info("I will create it ...");
									openFuxmlBaseDir.mkdirs();
								} break;
		}
	}
	
	private void getVersion()
	{
		openFuxmlVersion = this.getClass().getPackage().getImplementationVersion();
		if(openFuxmlVersion==null)
		{
			startupenv = StartUpEnv.DEVELOPER;
			String propName = "resources/properties/ant.properties";
			try
			{
				logger.info("Developing Environmet. Using "+propName+" to get Version");
				InputStream is = MultiResourceLoader.searchIs(this.getClass().getClassLoader(), propName);
				Properties versionProperties = new Properties();
				versionProperties.load(is);
				openFuxmlVersion = versionProperties.getProperty("openfuxml-version");
			}
			catch (FileNotFoundException e) {logger.error("File is missing! "+propName);}
			catch (IOException e) {logger.error(e);}
		}
		else {startupenv = StartUpEnv.DEVELOPER;}
		logger.info("OpenFuXML Version: "+openFuxmlVersion);
	}
	
	public StartUpEnv getStartupenv() {return startupenv;}
	public String getOpenFuxmlVersion() {return openFuxmlVersion;}
	protected abstract Configuration getConfiguration();
}
