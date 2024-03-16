package org.openfuxml;

import net.sf.exlp.exception.ExlpConfigurationException;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.config.ExlpCentralConfigPointer;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxClientBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxClientBootstrap.class);
		
	public static final String cfgKeyLatexTarget = "ofx.latex.basedir";
	
	public static String xmlConfig = "ofx/client/config/ofx.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerBootstrap.instance("cli.client.log4j2.xml").path("ofx/system/io/log").init();

		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
		
		try
		{
			ExlpCentralConfigPointer ccp = ExlpCentralConfigPointer.instance("ofx").jaxb(JaxbUtil.instance());
			ConfigLoader.addFile(ccp.toFile("core"));
		}
		catch (ExlpConfigurationException e)
		{
			logger.warn("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+": "+e.getMessage());
		}
		ConfigLoader.addString(configFile);
		Configuration config = ConfigLoader.init();					
		logger.debug("Config and Logger initialized");

		return config;
	}
}