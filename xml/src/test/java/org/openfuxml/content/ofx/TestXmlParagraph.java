package org.openfuxml.content.ofx;

import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlParagraph extends AbstractXmlOfxTest<Paragraph>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlParagraph(){super(Paragraph.class);}
	public static Paragraph create(boolean withChildren){return (new TestXmlParagraph()).build(withChildren);}
   
    public Paragraph build(boolean withChilds)
    {
    	Paragraph xml = new Paragraph();
    	xml.setLang("en");
    	
    	logger.warn("Not fully implemented");
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlParagraph test = new TestXmlParagraph();
		test.saveReferenceXml();
    }
}