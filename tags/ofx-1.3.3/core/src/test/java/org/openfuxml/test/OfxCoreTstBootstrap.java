package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxCoreTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxCoreTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("src/test/resources/config.ofx-core.test");
		loggerInit.init();		
	}
}