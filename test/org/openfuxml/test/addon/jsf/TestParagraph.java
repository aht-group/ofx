package org.openfuxml.test.addon.jsf;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.content.ofx.Emphasis;
import org.openfuxml.content.ofx.Paragraph;

public class TestParagraph
{
	static Log logger = LogFactory.getLog(TestParagraph.class);
	
	public TestParagraph()
	{
		
	}
	
	public void xmlConstruct()
	{	
		Paragraph p = new Paragraph();
		p.getContent().add("Test");
		
		Emphasis e = new Emphasis();
		e.setBold(true);
		e.setValue("XX");
		p.getContent().add(e);
		
		Document doc = JaxbUtil.toDocument(p);
		doc = JDomUtil.correctNsPrefixes(doc, new JsfNsPrefixMapper());
		JDomUtil.debug(doc);
	}
		
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing XSD Taglib");
			
		TestParagraph test = new TestParagraph();
		test.xmlConstruct();
	}
}