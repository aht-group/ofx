package org.openfuxml.content.layout;

import org.openfuxml.model.xml.core.layout.Spacing;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlSpacing extends AbstractXmlLayoutTest<Spacing>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlSpacing(){super(Spacing.class);}
	public static Spacing create(boolean withChildren){return (new TestXmlSpacing()).build(withChildren);}
   
    public Spacing build(boolean withChilds)
    {
    	Spacing xml = new Spacing();
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
		TestXmlSpacing test = new TestXmlSpacing();
		test.saveReferenceXml();
    }
}