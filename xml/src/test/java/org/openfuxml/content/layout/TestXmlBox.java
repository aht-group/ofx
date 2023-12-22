package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Box;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlBox extends AbstractXmlLayoutTest<Box>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlBox(){super(Box.class);}
	public static Box create(boolean withChildren){return (new TestXmlBox()).build(withChildren);}
   
    public Box build(boolean withChilds)
    {
    	Box xml = new Box();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlBox test = new TestXmlBox();
		test.saveReferenceXml();
    }
}