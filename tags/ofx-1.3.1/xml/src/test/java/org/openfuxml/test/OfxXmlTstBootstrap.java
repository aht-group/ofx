package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxXmlTstBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxXmlTstBootstrap.class);
		
	public static void init()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("ofx-xml.test");
		loggerInit.init();		
	}
}