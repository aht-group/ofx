package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexRowRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexRowRenderer.class);
	
	private static enum Key {standard}
	
	private LatexRowRenderer renderer;
	private String dir = "row";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexRowRenderer(cmm,dsm);
	}
	
	@After public void close(){renderer=null;}
	
	public static Row create()
	{
		int[] words = {10,10};
		return create(words);
	}
	
	public static Row create(int[] words)
	{
		Row row = new Row();
		for(int i=0;i<words.length;i++)
		{
			row.getCell().add(TestLatexCellRenderer.create(words[i]));
		}
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
			
    	TestLatexRowRenderer.initLoremIpsum();
    	TestLatexRowRenderer test = new TestLatexRowRenderer();
    	test.setEnvironment(true);
    	
 //   	test.initRenderer();test.paragraph();
    	test.initRenderer();test.standard();
    }
}