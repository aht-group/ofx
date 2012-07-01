package org.openfuxml.test;

import java.io.File;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("ofx-xml.test");
		loggerInit.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(Object xml, File f,boolean formattedOutput)
	{
		logger.debug("Saving Reference XML");
		
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, formattedOutput);
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11));
	}
}