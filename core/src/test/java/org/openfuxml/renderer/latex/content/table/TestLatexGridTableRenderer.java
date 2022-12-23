package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexGridTableRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexGridTableRenderer.class);
	
	private static enum Key {table,tableWithHead}
	
	private LatexTableRenderer renderer;
	private String dir = "grid";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexTableRenderer(cp);
		renderer.setPreBlankLine(false);
	}
	
	@After public void close(){renderer=null;}

    @Test @Ignore
    public void table() throws IOException, OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	referenceFile = new File(rootDir,dir+"/"+Key.table+".tex");
    	renderer.render(table);
    	renderTest(renderer);
    }
    
    @Test @Ignore
    public void withHead() throws OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	table.getContent().setHead(TestLatexTableRenderer.createTableHead());
    	referenceFile = new File(rootDir,dir+"/"+Key.tableWithHead+".tex");
    	renderer.render(table);
    	OfxContentDebugger.debug(renderer.getContent());
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();
			
    	TestLatexGridTableRenderer.initLoremIpsum();
    	TestLatexGridTableRenderer test = new TestLatexGridTableRenderer();
    	test.setEnvironment(true);
    	
    	test.initRenderer();test.table();
    	test.initRenderer();test.withHead();
    }
}