package org.openfuxml.renderer.processor.latex.content.table;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.TestLatexParagraphFactory;
import org.openfuxml.renderer.processor.latex.content.list.TestLatexListFactory;
import org.openfuxml.test.OfxCoreTstBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexCellFactory extends AbstractLatexTableTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexCellFactory.class);
	
	private static enum Key {string,list}
	
	private LatexCellFactory renderer;
	private String dir = "cell";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexCellFactory();
	}
	
	@After public void close(){renderer=null;}
	
	public static Cell create()
	{
		Cell cell = new Cell();
    	cell.getContent().add(TestLatexParagraphFactory.create());
    	return cell;
	}
	
    @Test
    public void paragraph() throws IOException, OfxAuthoringException
    {    	    	
    	f = new File(rootDir,dir+"/"+Key.string+".txt");
    	renderer.render(create());
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test
    public void list() throws IOException, OfxAuthoringException
    {    	
    	Cell cell = new Cell();
    	cell.getContent().add(TestLatexListFactory.createList());
    	
    	f = new File(rootDir,dir+"/"+Key.list+".txt");
    	renderer.render(cell);
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTstBootstrap.init();
			
    	TestLatexCellFactory.initLoremIpsum();
    	TestLatexCellFactory test = new TestLatexCellFactory();
    	test.setSaveReference(true);
    	
 //   	test.initRenderer();test.paragraph();
    	test.initRenderer();test.list();

    }
   
}