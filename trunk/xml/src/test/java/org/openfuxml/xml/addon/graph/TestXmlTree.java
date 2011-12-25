package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlTree extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlTree.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"tree.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Tree actual = create();
    	Tree expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Tree.class);
    	assertJaxbEquals(expected, actual);
    }
   
    private static Tree create(){return create(true);}
    public static Tree create(boolean withChilds)
    {
    	Tree xml = new Tree();
    	
    	if(withChilds)
    	{
    		xml.setNode(TestXmlNode.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlTree.initFiles();	
		TestXmlTree test = new TestXmlTree();
		test.save();
    }
}