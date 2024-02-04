package org.openfuxml.test;

import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxJsfTestBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxJsfTestBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.path("config.ofx-jsf.test");
		loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}