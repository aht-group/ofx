package org.openfuxml.util.config.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Files;
import org.openfuxml.util.config.jaxb.Dirs.Dir;
import org.openfuxml.util.config.jaxb.Files.File;

import de.kisner.util.io.resourceloader.MultiResourceLoader;

public class ConfFileFactory
{
	static Logger logger = Logger.getLogger(ConfFileFactory.class);
	
	private Properties properties;
	
	public ConfFileFactory()
	{
		String resource = "resources/properties/ant.properties";
		InputStream is;
		try
		{
			is = MultiResourceLoader.searchIs(this.getClass().getClassLoader(),resource);
			properties = new Properties();
			properties.load(is);
		}
		catch (FileNotFoundException e) {logger.warn("This should only happen in Developing Environments",e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public Files getFiles()
	{
		Files files = new Files();

		File file = new File();
			file.setType("lib");
			file.setContent("ant-"+properties.getProperty("ant-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("ant-launcher-"+properties.getProperty("ant-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("openfuxml-ss-"+properties.getProperty("openfuxml-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("ofxlib");
			file.setContent("openfuxml-"+properties.getProperty("openfuxml-version")+".jar");
			files.getFile().add(file);
		file = new File();
			file.setType("lib");
			file.setContent("saxon-"+properties.getProperty("saxon-version")+".jar");
			files.getFile().add(file);
		
		return files;
	}
}
