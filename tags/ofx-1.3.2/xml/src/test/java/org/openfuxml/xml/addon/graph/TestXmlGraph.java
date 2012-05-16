package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlGraph extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlGraph.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"graph.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Graph actual = create(true);
    	Graph expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Graph.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Graph create(boolean withChilds)
    {
    	Graph xml = new Graph();
    	
    	if(withChilds)
    	{
    		xml.setEdges(TestXmlEdges.create(false));
    		xml.setEdges(TestXmlEdges.create(false));
    		xml.setNodes(TestXmlNodes.create(false));
    		xml.setNodes(TestXmlNodes.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlGraph.initFiles();	
		TestXmlGraph test = new TestXmlGraph();
		test.save();
    }
}