package org.openfuxml.content.list;

import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlList extends AbstractXmlListTest<List>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlList.class);
	
	public TestXmlList(){super(List.class);}
	public static List create(boolean withChildren){return (new TestXmlList()).build(withChildren);}
   
    public List build(boolean withChildren)
    {
    	List xml = new List();
    	
    	if(withChildren)
    	{
    		xml.setComment(TestXmlComment.create(false));
    		xml.setType(TestXmlType.create(false));
    		
    		xml.getItem().add(TestXmlItem.create(false));xml.getItem().add(TestXmlItem.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
		TestXmlList test = new TestXmlList();
		test.saveReferenceXml();
    }
}