package org.openfuxml.content.text;

import org.openfuxml.model.xml.core.text.Emphasis;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEmphasis extends AbstractXmlOfxTextTest<Emphasis>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlEmphasis.class);
	
	public static TestXmlEmphasis instance() {return new TestXmlEmphasis();}
	private TestXmlEmphasis() {super(Emphasis.class);}
	   
    public Emphasis build(boolean withChildren)
    {
    	Emphasis xml = new Emphasis();
    	xml.setBold(true);
    	xml.setItalic(true);
    	xml.setQuote(true);
    	xml.setStyle("typewriter");
    	xml.setValue("myEmphasise");
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlEmphasis.instance().saveReferenceXml();
    }
}