package org.openfuxml.model.xml.content.list;

import org.openfuxml.content.layout.TestXmlLayout;
import org.openfuxml.content.ofx.TestXmlComment;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlList extends AbstractXmlListTest<List>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlList.class);
	
	public static TestXmlList instance() {return new TestXmlList();}
	private TestXmlList() {super(List.class);}
   
    public List build(boolean wChildren)
    {
    	List xml = new List();
    	
    	if(wChildren)
    	{
    		xml.setComment(TestXmlComment.create(false));
    		xml.setType(TestXmlType.instance().build(false));
    		xml.setLayout(TestXmlLayout.create(false));
    		
    		xml.getItem().add(TestXmlItem.instance().build(false));
    		xml.getItem().add(TestXmlItem.instance().build(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlList.instance().saveReferenceXml();
    }
}