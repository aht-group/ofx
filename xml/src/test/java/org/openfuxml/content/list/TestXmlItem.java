package org.openfuxml.content.list;

import org.openfuxml.content.ofx.TestXmlParagraph;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlItem extends AbstractXmlListTest<Item>
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlItem.class);
	
	public TestXmlItem(){super(Item.class);}
	public static Item create(boolean withChildren){return (new TestXmlItem()).build(withChildren);}
   
    public Item build(boolean withChildren)
    {
    	Item xml = new Item();
    	xml.setLang("myLang");
    	xml.setName("myName");
    	xml.getContent().add("myMixed");
    	
    	if(withChildren)
    	{
    		xml.getContent().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();	
		TestXmlItem test = new TestXmlItem();
		test.saveReferenceXml();
    }
}