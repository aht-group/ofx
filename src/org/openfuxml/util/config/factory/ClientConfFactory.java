package org.openfuxml.util.config.factory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Dirs;
import org.openfuxml.util.config.jaxb.Files;
import org.openfuxml.util.config.jaxb.Net;
import org.openfuxml.util.config.jaxb.Openfuxml;
import org.openfuxml.util.config.jaxb.Dirs.Dir;

import de.kisner.util.LoggerInit;

public class ClientConfFactory
{
	static Logger logger = Logger.getLogger(ClientConfFactory.class);
	
	private Openfuxml openfuxml;
	
	public ClientConfFactory()
	{
		
	}
	
	private Net getNet()
	{
		Net net =  new Net();
		net.setServer("testN");
		return net;
	}
	
	private Dirs getDirs()
	{
		Dirs dirs = new Dirs();
	
		Dir dir = new Dir();
			dir.setType("basedir");
			dirs.getDir().add(dir);
		 
		return dirs;
	}
	
	private Files getFiles()
	{
		Files files = new Files();
		files.setServer("testF");
		return files;
	}
	
	public void createConfig()
	{
		openfuxml = new Openfuxml();
		openfuxml.setNet(getNet());
		openfuxml.setDirs(getDirs());
		openfuxml.setFiles(getFiles());
	}
	
	public void writeConfig()
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(Openfuxml.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( openfuxml, System.out);
			
		}
		catch (JAXBException e) {logger.error(e);}
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		ClientConfFactory ccf = new ClientConfFactory();
			ccf.createConfig();
			ccf.writeConfig();
	}
}
