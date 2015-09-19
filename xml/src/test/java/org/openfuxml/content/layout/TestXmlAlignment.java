package org.openfuxml.content.layout;

import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlAlignment extends AbstractXmlLayoutTest<Alignment>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlAlignment(){super(Alignment.class);}
	public static Alignment create(boolean withChildren){return (new TestXmlAlignment()).build(withChildren);}
     
    public Alignment build(boolean withChilds)
    {
    	Alignment xml = new Alignment();
    	xml.setHorizontal("center");
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlAlignment test = new TestXmlAlignment();
		test.saveReferenceXml();
    }
}