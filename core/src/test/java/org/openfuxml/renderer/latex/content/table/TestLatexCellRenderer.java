package org.openfuxml.renderer.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.list.TestLatexListRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexCellRenderer extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexCellRenderer.class);
	
	private static enum Key {string,list,specialChar}
	
	private LatexCellRenderer renderer;
	private String dir = "cell";
	
	@Before
	public void initRenderer()
	{
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
    public void paragraph() throws IOException, OfxAuthoringException
    {    	    	
    	f = new File(rootDir,dir+"/"+Key.string+".txt");
    	renderer.render(create());
    	debugCharacter(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test
    public void list() throws IOException, OfxAuthoringException
    {    	
    	Cell cell = new Cell();
    	cell.getContent().add(TestLatexListRenderer.createList());
    	
    	f = new File(rootDir,dir+"/"+Key.list+".txt");
    	renderer.render(cell);
    	debugCharacter(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test
    public void specialChar() throws IOException, OfxAuthoringException
    {
    	Paragraph p = new Paragraph();
    	p.getContent().add("Monitoring & Evaluation");
    	
    	Cell cell = new Cell();
    	cell.getContent().add(p);
    	
    	f = new File(rootDir,dir+"/"+Key.specialChar+".txt");
    	renderer.render(cell);
    	debugCharacter(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexCellRenderer.initLoremIpsum();
    	TestLatexCellRenderer test = new TestLatexCellRenderer();
    	test.setEnvironment(true);
    	
    	test.initRenderer();test.paragraph();
    	test.initRenderer();test.list();
    	test.initRenderer();test.specialChar();
    }
}