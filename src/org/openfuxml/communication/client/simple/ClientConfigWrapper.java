package org.openfuxml.communication.client.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Element;

import de.kisner.util.xml.XmlConfig;
import de.kisner.util.xml.XmlElementNotFoundException;

public class ClientConfigWrapper
{
	static Logger logger = Logger.getLogger(ClientConfigWrapper.class);
	public static enum Typ {direct,socket};
	
	private static XmlConfig xCnf;
	private static Properties myProperties;
	
	public static Typ typ;
	public static String host, repository;
	public static int port;
	
	public static void init(XmlConfig xmlCnf)
	{
		xCnf=xmlCnf;
		
		host = xCnf.getAttribute("net/server", "host");		
		port = xCnf.getIntAttribute("net/server", "port");
		repository = xCnf.getText("dirs/dir[@typ=\"repository\"]");
		
		checkDefaultProperties();
		setConnectionType();
	}
	
	public static void setServer(String host, String Port)
	{
		logger.warn("Not implemented!! (Server="+host+", port="+port+")");
	}
	
	public static String getServerDir(String typ)
	{
		return xCnf.getText("dirs/dir[@typ=\""+typ+"\"]");
	}
	
	public static String getClientConf(String key)
	{
		return xCnf.getText("client/value[@key=\""+key+"\"]");
	}
	
	private static void setConnectionType()
	{
		try{typ = Typ.valueOf(xCnf.getAttribute("net/server", "typ"));}
		catch (IllegalArgumentException e)
		{
			logger.fatal("Unknown <net><server type=\""+xCnf.getAttribute("net/server", "typ")+"\" ...");
			logger.fatal("Application will be shut down");
			try {Thread.sleep(5*1000);}
			catch (InterruptedException e1) {logger.error(e1);}
			System.exit(-1);
		}	
		logger.debug("Typ"+typ);
	}
	
	private static void checkDefaultProperties()
	{
		checkKeyValue("application","fuxml");
		checkKeyValue("project","");
		checkKeyValue("document","");
		checkKeyValue("format","");
		
		String openFuXMLDir=System.getProperty("user.home")+
		System.getProperties().getProperty("file.separator")+
		".openfuxml";

		File propFile = new File(openFuXMLDir,"simple-client.prop");
		myProperties = new Properties();
		if(propFile.exists())
		{
			logger.info("Lade Properties von "+propFile.toString());
			try {
				myProperties.load(new FileInputStream(propFile));
			}
			catch (IOException e)
			{
				logger.fatal("IOException", e);
			}
		}
		else
		{
			logger.info("Setting default properties. "+propFile.getAbsolutePath()+" does not exist.");
			myProperties.setProperty("Verzeichnis", System.getProperty("user.home"));
			myProperties.setProperty("Host","localhost");
			myProperties.setProperty("Port","4455");
		}
	}
	
	private static void checkKeyValue(String key, String defaultValue)
	{
		try{xCnf.getTextException("client/value[@key=\""+key+"\"]");}
		catch (XmlElementNotFoundException e)
		{
			Element xml = new Element("value");
			xml.setAttribute("key", key);
			xml.setText(defaultValue);
			try {xCnf.newElement("client", xml);}
			catch (XmlElementNotFoundException e1) {logger.error(e1.getMessage());}
			logger.debug("Setting default ("+key+"="+defaultValue+")");
		}
	}
	
	public static void updateKeyValue(String typ, String value)
	{
		Element xml = new Element("value");
		xml.setAttribute("key", typ);
		xml.setText(value);
		try {xCnf.updateElement("client/value[@key=\""+typ+"\"]", xml, false);}
		catch (XmlElementNotFoundException e) {e.printStackTrace();}
	}
	
	public static void debug(){xCnf.debug();}
	
	public static void updateServerDir(String typ, String value)
	{
		Element xml = new Element("dir");
		xml.setAttribute("typ", typ);
		xml.setText(value);
		try {xCnf.updateElement("dirs/dir[@typ=\""+typ+"\"]", xml, false);}
		catch (XmlElementNotFoundException e) {e.printStackTrace();}
		debug();
	}
}
