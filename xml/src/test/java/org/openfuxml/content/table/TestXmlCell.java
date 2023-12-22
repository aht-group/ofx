package org.openfuxml.content.table;

import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCell extends AbstractXmlTableTest<Cell>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlCell(){super(Cell.class);}
	public static Cell create(boolean withChildren){return (new TestXmlCell()).build(withChildren);}
   
    public Cell build(boolean withChilds)
    {
    	Cell xml = new Cell();
    	
    	if(withChilds)
    	{
//    		xml.getContent().add(TestXmlImage.create(false));
//    		xml.getContent().add(TestXmlParagraph.create(false));
 //   		xml.getContent().add(TestXmlList.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();	
		TestXmlCell test = new TestXmlCell();
		test.saveReferenceXml();
    }
}