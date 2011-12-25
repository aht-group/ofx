package org.openfuxml.renderer.processor.latex.addon.graph;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTstBootstrap;
import org.openfuxml.xml.addon.graph.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexTreeNodeRenderer extends AbstractLatexGraphTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexTreeNodeRenderer.class);
	
	private static enum Key {simple}
	
	private LatexTreeNodeRenderer renderer;
	private String dir = "treeNode";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTreeNodeRenderer();
	}
	
	@After public void close(){renderer=null;}
	
	public static Node createNode(){return createNode("myLabel");}
	public static Node createNode(String label)
	{
		Node node = new Node();
		node.setLabel(label);
		return node;
	}
	
    @Test
    public void simpleNode() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.simple+".tex");
    	Node node = createNode();
    	renderer.render(node);
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTstBootstrap.init();
			
    	TestLatexTreeNodeRenderer.initLoremIpsum();
    	TestLatexTreeNodeRenderer test = new TestLatexTreeNodeRenderer();
    	test.setSaveReference(true);
    	
    	test.initRenderer();test.simpleNode();
    }
}