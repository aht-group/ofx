package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlWidth extends AbstractXmlLayoutTest<Width>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlWidth(){super(Width.class);}
	public static Width create(boolean withChildren){return (new TestXmlWidth()).build(withChildren);}
   
    public Width build(boolean withChilds)
    {
    	Width xml = new Width();
    	xml.setValue(12.3);
    	xml.setFlex(false);
    	xml.setNarrow(false);
    	xml.setUnit("pt");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlWidth test = new TestXmlWidth();
		test.saveReferenceXml();
    }
}