package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlNode extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlNode.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"node.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Node actual = create();
    	Node expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Node.class);
    	assertJaxbEquals(expected, actual);
    }
   
    private static Node create(){return create(true);}
    public static Node create(boolean withChilds)
    {
    	Node xml = new Node();
    	xml.setId(123);
    	xml.setCode("myCode");
    	xml.setLabel("myLabel");
    	
    	if(withChilds)
    	{
    		xml.getNode().add(TestXmlNode.create(false));
    		xml.getNode().add(TestXmlNode.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlNode.initFiles();	
		TestXmlNode test = new TestXmlNode();
		test.save();
    }
}