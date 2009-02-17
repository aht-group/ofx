package org.openfuxml.util.config.factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.util.config.jaxb.Openfuxml;
import org.openfuxml.util.config.jaxb.Openfuxml.Net;

import de.kisner.util.ConfigLoader;
import de.kisner.util.LoggerInit;
import de.kisner.util.architecture.ArchUtil;

public class ClientConfFactory extends AbstractConfFactory
{
	static Logger logger = Logger.getLogger(ClientConfFactory.class);
	
	private Openfuxml openfuxml;
	private String mainConf;
	
	public ClientConfFactory()
	{

	}
	
	public Configuration getConfiguration()
	{
		ConfigLoader.add(openFuxmlBaseDir+fs+mainConf);
		ConfigLoader.add("resources"+fs+"config"+fs+"client-images.xml");
		Configuration config = ConfigLoader.init();
		return config;
	}
	
	public void init(String mainConf)
	{
		this.mainConf=mainConf;
		File fConf = new File(openFuxmlBaseDir,mainConf);
		logger.debug("Searching "+fConf);
		try
		{
			if(!fConf.exists())
			{
				logger.info("No "+mainConf+" found. I will create a default one.");
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
		
		Openfuxml.Server server = new Openfuxml.Server();
			server.setContent("direct");
			server.setVersion(openFuxmlVersion);
		
		openfuxml.setServer(server);
		openfuxml.setNet(getNet());
		openfuxml.setDirs(cdf.getDirs(startupenv,openFuxmlBaseDir.getAbsolutePath(),openFuxmlVersion));
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
