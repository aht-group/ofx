package org.openfuxml.renderer.latex.table;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.controller.provider.text.ParagraphProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.list.TestLatexListRenderer;
import org.openfuxml.renderer.latex.content.table.LatexCellRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexCellRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexCellRenderer.class);
	
	private static enum Key {string,list,specialChar}
	
	private LatexCellRenderer renderer;
	
	@Before
	public void initRenderer()
	{
		super.initDir("table/cell");
		renderer = new LatexCellRenderer(cp);
	}
	
	@After public void close(){renderer=null;}
	
	public static Cell create(){return create(10);}
	public static Cell create(int words)
	{
		Cell cell = new Cell();
    	cell.getContent().add(ParagraphProvider.create(words));
    	return cell;
	}
	
    @Test
    public void string() throws IOException, OfxAuthoringException
    {    	    	
    	initFile(Key.string);
    	renderer.render(create());
    	renderTest(renderer);
    }
    
    @Test
    public void list() throws IOException, OfxAuthoringException
    {    	
    	Cell cell = new Cell();
    	cell.getContent().add(TestLatexListRenderer.createList());
    	
    	initFile(Key.list);
    	renderer.render(cell);
    	renderTest(renderer);
    }
    
    @Test
    public void specialChar() throws IOException, OfxAuthoringException
    {
    	Paragraph p = new Paragraph();
    	p.getContent().add("Monitoring & Evaluation");
    	
    	Cell cell = new Cell();
    	cell.getContent().add(p);
    	
    	initFile(Key.specialChar);
    	renderer.render(cell);
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();
			
    	TestLatexCellRenderer.initLoremIpsum();
    	TestLatexCellRenderer test = new TestLatexCellRenderer();
    	test.setEnvironment(true);
    	
//    	test.initRenderer();test.string();
//    	test.initRenderer();test.list();
//   	test.initRenderer();test.specialChar();
    }
}