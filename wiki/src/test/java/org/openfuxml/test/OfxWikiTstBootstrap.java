package org.openfuxml.test;

import org.exlp.util.io.log.LoggerInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxWikiTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxWikiTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.path("src/test/resources/config");
		loggerInit.init();		
	}
}