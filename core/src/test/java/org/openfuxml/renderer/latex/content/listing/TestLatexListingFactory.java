package org.openfuxml.renderer.latex.content.listing;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.xml.content.list.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexListingFactory extends AbstractLatexListingTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexListingFactory.class);
	
	private LatexListingRenderer renderer;
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexListingRenderer();
	}
	@After public void close(){renderer=null;}
	
	public static Item createItem(){return createItem(null);}
	public static Item createItem(String name)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(10));
		
		Item item = new Item();
		item.setName(name);
		item.getContent().add(p);
		return item;
	}
	
    @Test
    public void test() throws IOException, OfxAuthoringException
    {
    	Paragraph p = new Paragraph();
    	p.getContent().add("Test");
    	renderer.render(p);
    	debug(renderer);
 //   	save(renderer,f);
  //  	assertText(renderer,f);
    }
   
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexListingFactory.initLoremIpsum();
    	TestLatexListingFactory test = new TestLatexListingFactory();
    	test.setSaveReference(true);
    	
    	test.initRenderer();
    	test.test();
    }
   
}