package org.openfuxml.test;

import net.sf.ahtutils.test.AbstractAhtUtilsXmlTest;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJsfTest extends AbstractAhtUtilsXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfTest.class);	
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
			loggerInit.addAltPath("config.ofx-jsf.test");
			loggerInit.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
    }
}