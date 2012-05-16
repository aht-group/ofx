package org.openfuxml.addon.graph;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.xml.addon.graph.Edges;
import org.openfuxml.xml.addon.graph.Graph;
import org.openfuxml.xml.addon.graph.Nodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGraphDotConverter
{	
	final static Logger logger = LoggerFactory.getLogger(TestGraphDotConverter.class);

	private Graph xml;
	
	@Before
	public void init()
	{
		xml = new Graph();
		xml.setNodes(new Nodes());
		xml.setEdges(new Edges());
	}
	
    @Test
    public void convert()
    {
    	
    }
}