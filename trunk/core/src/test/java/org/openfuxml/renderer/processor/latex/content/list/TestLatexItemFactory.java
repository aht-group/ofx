package org.openfuxml.renderer.processor.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.test.OfxCoreTstBootstrap;
import org.openfuxml.xml.content.list.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexItemFactory extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexItemFactory.class);
	
	private static enum Key {itemize,description}
	private LatexItemFactory renderer;
	private String dir = "item";
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexItemFactory();
	}
	
	@After public void close(){renderer=null;}
	
	public Item createItem()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(10));
		
		Item item = new Item();
		item.setName("name");
		item.getContent().add(p);
		return item;
	}
	
    @Test
    public void render() throws IOException
    {
    	f = new File(rootDir,dir+"/"+Key.itemize+".txt");
    	renderer.render(createItem());
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
   
    public static Auth create()
    {
    	Auth xml = new Auth();
    	xml.setType("wiki");
    	xml.setName("name");
    	xml.setPassword("xyz");
    	return xml;
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTstBootstrap.init();
			
    	TestLatexItemFactory.initLoremIpsum();
    	TestLatexItemFactory test = new TestLatexItemFactory();
    	test.setSaveReference(true);
    	test.initRenderer();
		test.render();
    }
   
}