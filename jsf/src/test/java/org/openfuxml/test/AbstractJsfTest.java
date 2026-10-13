package org.openfuxml.test;

import java.io.File;
import java.time.LocalDateTime;

import javax.xml.datatype.XMLGregorianCalendar;

import org.exlp.util.jx.JaxbUtil;
import org.exlp.util.system.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractJsfTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJsfTest.class);	
	
	@BeforeAll
    public static void initLogger()
	{
		OfxBootstrap.init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
    }
	
	protected void assertJaxbEquals(Object expected, Object actual)
	{
		Assertions.assertEquals(JaxbUtil.toString(expected),JaxbUtil.toString(actual),"Actual XML differes from expected XML");
	}
	
	protected static XMLGregorianCalendar getDefaultXmlDate()
	{
		LocalDateTime ldt = LocalDateTime.of(2011,11,11,11,11,11);
		return DateUtil.toXmlGc(ldt);
	}
	
	protected void save(Object xml, File f)
	{
		logger.debug("Saving Reference XML");
		JaxbUtil.debug(xml);
    	JaxbUtil.save(f, xml, true);
	}
}