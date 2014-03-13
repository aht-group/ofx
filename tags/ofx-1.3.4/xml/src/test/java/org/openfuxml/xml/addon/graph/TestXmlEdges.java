package org.openfuxml.xml.addon.graph;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.test.OfxXmlTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlEdges extends AbstractXmlGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestXmlEdges.class);
	
	@BeforeClass
	public static void initFiles()
	{
		fXml = new File(rootDir,"edges.xml");
	}
    
    @Test
    public void testXml() throws FileNotFoundException
    {
    	Edges actual = create(true);
    	Edges expected = JaxbUtil.loadJAXB(fXml.getAbsolutePath(), Edges.class);
    	assertJaxbEquals(expected, actual);
    }
   
    public static Edges create(boolean withChilds)
    {
    	Edges xml = new Edges();
    	
    	if(withChilds)
    	{
    		xml.getEdge().add(TestXmlEdge.create(false));
    		xml.getEdge().add(TestXmlEdge.create(false));
    	}
    	
    	return xml;
    }
    
    public void save() {save(create(true),fXml,false);}
	
	public static void main(String[] args)
    {
		OfxXmlTstBootstrap.init();
			
		TestXmlEdges.initFiles();	
		TestXmlEdges test = new TestXmlEdges();
		test.save();
    }
}