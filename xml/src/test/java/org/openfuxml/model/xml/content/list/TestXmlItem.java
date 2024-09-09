package org.openfuxml.model.xml.content.list;

import org.openfuxml.content.ofx.TestXmlParagraph;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlItem extends AbstractXmlListTest<Item>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlItem.class);
	
	public static TestXmlItem instance() {return new TestXmlItem();}
	private TestXmlItem() {super(Item.class);}
   
    @Override public Item build(boolean wChildren)
    {
    	Item xml = new Item();
    	xml.setLang("myLang");
    	xml.setName("myName");
    	xml.getContent().add("myMixed");
    	
    	if(wChildren)
    	{
    		xml.getContent().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		TestXmlItem.instance().saveReferenceXml();
    }
}