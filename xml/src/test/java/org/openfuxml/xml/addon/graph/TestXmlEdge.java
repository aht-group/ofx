package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEdge extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlEdge.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"edge.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Edge actual = create(true);
    	Edge expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Edge.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Edge create(boolean withChilds)
    {
    	Edge xml = new Edge();
    	xml.setId(123);
    	xml.setFrom(1);
    	xml.setTo(2);
    	xml.setDirected(true);
    	xml.setType("myType");
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlEdge.initFiles();	
		TestXmlEdge test = new TestXmlEdge();
		test.save();
    }
}