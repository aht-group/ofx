package org.openfuxml.content.ofx;

import org.openfuxml.model.xml.core.ofx.Sections;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSections extends AbstractXmlOfxTest<Sections>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlSections(){super(Sections.class);}
	public static Sections create(boolean withChildren){return (new TestXmlSections()).build(withChildren);}
   
    public Sections build(boolean withChilds)
    {
    	Sections xml = new Sections();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlSections test = new TestXmlSections();
		test.saveReferenceXml();
    }
}