package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Float;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlFloat extends AbstractXmlLayoutTest<Float>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlFloat(){super(Float.class);}
	public static Float create(boolean withChildren){return (new TestXmlFloat()).build(withChildren);}
   
    public Float build(boolean withChildren)
    {
    	Float xml = new Float();
    	xml.setValue(true);
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();	
		TestXmlFloat test = new TestXmlFloat();
		test.saveReferenceXml();
    }
}