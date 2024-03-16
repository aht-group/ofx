package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Height;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlHeight extends AbstractXmlLayoutTest<Height>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlHeight(){super(Height.class);}
	public static Height create(boolean withChildren){return (new TestXmlHeight()).build(withChildren);}
   
    public Height build(boolean withChilds)
    {
    	Height xml = new Height();
    	xml.setValue(45.6);
    	xml.setUnit("pt");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlHeight test = new TestXmlHeight();
		test.saveReferenceXml();
    }
}