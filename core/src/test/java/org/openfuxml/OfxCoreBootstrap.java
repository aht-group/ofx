package org.openfuxml;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCoreBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxCoreBootstrap.class);
		
	public static final String cfgKeyLatexTarget = "ofx.latex.basedir";
	
	public static String xmlConfig = "config.ofx-core.test/ofx.xml";
	
	public static Configuration init()
	{
		return init(xmlConfig);
	}
	
	public static Configuration init(String configFile)
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");
			loggerInit.path("config.ofx-core.test");
			loggerInit.init();

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