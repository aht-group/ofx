package org.openfuxml.content.editorial;

import org.openfuxml.model.xml.core.editorial.Acronym;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAcronym extends AbstractXmlEditorialTest<Acronym>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
    
	public TestXmlAcronym(){super(Acronym.class);}
	public static Acronym create(boolean withChildren){return (new TestXmlAcronym()).build(withChildren);}

    public Acronym build(boolean withChildren)
    {
    	Acronym xml = new Acronym();
    	xml.setCode("myCode");
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlAcronym test = new TestXmlAcronym();
		test.saveReferenceXml();
    }
}