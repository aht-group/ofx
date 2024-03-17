package org.openfuxml.renderer.latex.table;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.table.LatexRowRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexRowRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexRowRenderer.class);
	
	private static enum Key {standard}
	
	private LatexRowRenderer renderer;
	
	@Before
	public void initRenderer()
	{
		super.initDir("table/row");
		renderer = new LatexRowRenderer(cp);
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
    	initFile(Key.standard);
    	renderer.render(create());
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
			
    	TestLatexRowRenderer.initLoremIpsum();
    	TestLatexRowRenderer test = new TestLatexRowRenderer();
    	test.setEnvironment(true);
    	
    	test.initRenderer();test.standard();
    }
}