package org.openfuxml.test;

import net.sf.exlp.util.io.LoggerInit;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJsfTldTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfTldTest.class);	
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
}