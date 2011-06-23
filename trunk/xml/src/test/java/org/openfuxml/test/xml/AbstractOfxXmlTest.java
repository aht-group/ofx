package org.openfuxml.test.xml;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

public class AbstractOfxXmlTest
{
	static Log logger = LogFactory.getLog(AbstractOfxXmlTest.class);	
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
}