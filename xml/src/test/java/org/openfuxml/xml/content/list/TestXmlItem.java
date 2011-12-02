package org.openfuxml.xml.content.list;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.model.xml.content.ofx.TestXmlParagraph;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.openfuxml.xml.content.list.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlItem extends AbstractXmlListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlItem.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"item.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Item actual = create();
    	Item expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Item.class);
    	assertJaxbEquals(expected, actual);
    }
   
    private static Item create(){return create(true);}
    public static Item create(boolean withChilds)
    {
    	Item xml = new Item();
    	xml.setName("myName");
    	
    	if(withChilds)
    	{
    		xml.getContent().add(TestXmlParagraph.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlItem.initFiles();	
		TestXmlItem test = new TestXmlItem();
		test.save();
    }
}