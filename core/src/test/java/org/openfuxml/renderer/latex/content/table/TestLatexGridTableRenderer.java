package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.util.OfxContentDebugger;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexGridTableRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexGridTableRenderer.class);
	
	private static enum Key {table,tableWithHead}
	
	private LatexTableRenderer renderer;
	private String dir = "grid";
	
	@BeforeEach
	public void initRenderer()
	{
		renderer = new LatexTableRenderer(cp);
		renderer.setPreBlankLine(false);
	}
	
	@AfterEach public void close(){renderer=null;}

    @Test @Disabled
    public void table() throws IOException, OfxAuthoringException
    {
    	Table table = TestLatexTableRenderer.createTable();
    	referenceFile = new File(rootDir,dir+"/"+Key.table+".tex");
    	renderer.render(table);
    	renderTest(renderer);
    }
    
    @Test @Disabled
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
    	OfxBootstrap.init();
			
    	TestLatexGridTableRenderer.initLoremIpsum();
    	TestLatexGridTableRenderer test = new TestLatexGridTableRenderer();
    	test.setEnvironment(true);
    	
    	test.initRenderer();test.table();
    	test.initRenderer();test.withHead();
    }
}