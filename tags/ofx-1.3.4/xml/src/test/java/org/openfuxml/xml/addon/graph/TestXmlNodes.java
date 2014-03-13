package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlNodes extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlNodes.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"nodes.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Nodes actual = create(true);
    	Nodes expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Nodes.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Nodes create(boolean withChilds)
    {
    	Nodes xml = new Nodes();
    	
    	if(withChilds)
    	{
    		xml.getNode().add(TestXmlNode.create(false));
    		xml.getNode().add(TestXmlNode.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlNodes.initFiles();	
		TestXmlNodes test = new TestXmlNodes();
		test.save();
    }
}