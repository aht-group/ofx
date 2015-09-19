package org.openfuxml.content.layout;

import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlLayout extends AbstractXmlLayoutTest<Layout>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlLayout(){super(Layout.class);}
	public static Layout create(boolean withChildren){return (new TestXmlLayout()).build(withChildren);}
   
    public Layout build(boolean withChilds)
    {
    	Layout xml = new Layout();
    	
    	
    	if(withChilds)
    	{
    		xml.getLine().add(TestXmlLine.create(false));xml.getLine().add(TestXmlLine.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlLayout test = new TestXmlLayout();
		test.saveReferenceXml();
    }
}