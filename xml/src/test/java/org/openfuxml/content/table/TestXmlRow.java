package org.openfuxml.content.table;

import org.openfuxml.content.layout.TestXmlLayout;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlRow extends AbstractXmlTableTest<Row>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlRow(){super(Row.class);}
	public static Row create(boolean withChildren){return (new TestXmlRow()).build(withChildren);}
   
    public Row build(boolean withChilds)
    {
    	Row xml = new Row();
    	
    	if(withChilds)
    	{
    		xml.setLayout(TestXmlLayout.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlRow test = new TestXmlRow();
		test.saveReferenceXml();
    }
}