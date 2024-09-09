package org.openfuxml.test;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxBootstrap.class);
	
	private enum System {ofx}
	public static String xmlConfig = "ofx/system/property/xml-test.xml";
	
	private static Configuration config;
	
	public static Configuration init() {return init(xmlConfig);}
	public static Configuration init(String configFile)
	{
		LoggerBootstrap.instance().path("ofx/system/io/log").init();
//		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance(System.ofx).jaxb(JaxbUtil.instance());
			ConfigLoader configBootstrap = ConfigLoader.instance();
//			configBootstrap.add(ccp.toPath("client"));
//			configBootstrap.add(configFile);
			config = configBootstrap.combine();
		}
		catch (ConfigurationException e) {e.printStackTrace();}

		return config;
	}
}