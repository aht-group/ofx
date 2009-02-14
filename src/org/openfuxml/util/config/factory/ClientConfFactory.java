package org.openfuxml.util.config.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Openfuxml;
import org.openfuxml.util.config.jaxb.Openfuxml.Net;

import de.kisner.util.LoggerInit;

public class ClientConfFactory
{
	static Logger logger = Logger.getLogger(ClientConfFactory.class);
	
	private Openfuxml openfuxml;
	
	public ClientConfFactory()
	{
		
	}
	
	public void init(File fConf)
	{
		try
		{
			if(!fConf.exists())
			{
				createConfig();
				FileOutputStream fos = new FileOutputStream(fConf);
				writeConfig(fos);
			}
		}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	private Net getNet()
	{
		Net net =  new Net();
		net.setHost("host");
		net.setPort(4444);
		return net;
	}
	
	public void createConfig()
	{
		ConfDirFactory cdf = new ConfDirFactory();
		ConfFileFactory cff = new ConfFileFactory();
		
		openfuxml = new Openfuxml();
		openfuxml.setServer("direct");
		openfuxml.setNet(getNet());
		openfuxml.setDirs(cdf.getDirs());
		openfuxml.setFiles(cff.getFiles());
	}
	
	public void writeConfig(OutputStream os)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(Openfuxml.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE ); 
			m.marshal( openfuxml, os);
			os.close();
		}
		catch (JAXBException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public static void main(String args[]) 
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
			
		ClientConfFactory ccf = new ClientConfFactory();
			ccf.createConfig();
			ccf.writeConfig(System.out);
	}
}
