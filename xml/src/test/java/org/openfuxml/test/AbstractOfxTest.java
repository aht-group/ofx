package org.openfuxml.test;

import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

public abstract class AbstractOfxTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
		
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.path("ofx-xml.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		logger.warn("Init "+OfxNsPrefixMapper.class.getSimpleName());
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}