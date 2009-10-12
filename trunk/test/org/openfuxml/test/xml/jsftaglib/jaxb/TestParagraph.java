package org.openfuxml.test.xml.jsftaglib.jaxb;

import net.sf.exlp.util.JDomUtil;
import net.sf.exlp.util.JaxbUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.openfuxml.addon.jsf.data.jaxb.Emphasis;
import org.openfuxml.addon.jsf.data.jaxb.JsfNsPrefixMapper;
import org.openfuxml.addon.jsf.data.jaxb.Paragraph;

import de.kisner.util.LoggerInit;

public class TestParagraph
{
	private static Logger logger = Logger.getLogger(TestParagraph.class);
	
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