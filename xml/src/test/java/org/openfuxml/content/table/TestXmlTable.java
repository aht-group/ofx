package org.openfuxml.content.table;

import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.content.ofx.TestXmlTitle;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.test.AbstractOfxXmlTest;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTable extends AbstractXmlTableTest<Table>
{	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxXmlTest.class);
	
	public TestXmlTable(){super(Table.class);}
	public static Table create(boolean withChildren){return (new TestXmlTable()).build(withChildren);}
   
    public Table build(boolean withChilds)
    {
    	Table xml = new Table();
    	xml.setId("myId");
    	
    	if(withChilds)
    	{
    		xml.setTitle(TestXmlTitle.create(false));
    		xml.setSpecification(TestXmlSpecification.create(false));
    		xml.setComment(TestXmlComment.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();	
		TestXmlTable test = new TestXmlTable();
		test.saveReferenceXml();
    }
}