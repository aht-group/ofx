package org.openfuxml.test;

import java.io.File;

import javax.xml.datatype.XMLGregorianCalendar;

import net.sf.exlp.util.DateUtil;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxXmlTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	protected static NsPrefixMapperInterface nsPrefixMapper;
	
	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("ofx-xml.test");
		loggerInit.init();
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assert.assertEquals("XML-ref differes from XML-test",JaxbUtil.toString(expected),JaxbUtil.toString(actual));
	}
	
	protected void save(Object xml, File f,boolean formattedOutput)
	{
		logger.debug("Saving Reference XML");
		
		JaxbUtil.debug2(this.getClass(),xml, getPrefixMapper());
    	JaxbUtil.save(f, xml, nsPrefixMapper, formattedOutput);
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		return DateUtil.getXmlGc4D(DateUtil.getDateFromInt(2011, 11, 11, 11, 11, 11));
	}
	
	private NsPrefixMapperInterface getPrefixMapper()
	{
		if(nsPrefixMapper==null)
		{
			nsPrefixMapper = new OfxNsPrefixMapper();
		}
		return nsPrefixMapper;
	}
}