package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexRowFactory extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexRowFactory.class);
	
	private static enum Key {standard}
	
	private LatexRowRenderer renderer;
	private String dir = "row";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexRowRenderer();
	}
	
	@After public void close(){renderer=null;}
	
	public static Row create()
	{
		Row row = new Row();
		row.getCell().add(TestLatexCellFactory.create());
		row.getCell().add(TestLatexCellFactory.create());
		return row;
	}
	
    @Test
    public void standard() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.standard+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexRowFactory.initLoremIpsum();
    	TestLatexRowFactory test = new TestLatexRowFactory();
    	test.setSaveReference(true);
    	
 //   	test.initRenderer();test.paragraph();
    	test.initRenderer();test.standard();
    }
}