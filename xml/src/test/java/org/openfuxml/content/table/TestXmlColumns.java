package org.openfuxml.content.table;

import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlColumns extends AbstractXmlTableTest<Columns>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlColumns(){super(Columns.class);}
	public static Columns create(boolean withChildren){return (new TestXmlColumns()).build(withChildren);}
   
    public Columns build(boolean withChilds)
    {
    	Columns xml = new Columns();
    	
    	if(withChilds)
    	{
    		
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlColumns test = new TestXmlColumns();
		test.saveReferenceXml();
    }
}