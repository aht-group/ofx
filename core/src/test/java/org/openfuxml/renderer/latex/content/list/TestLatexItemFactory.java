package org.openfuxml.renderer.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ListItemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexItemFactory extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexItemFactory.class);
	
	private static enum Key {itemize,description}
	private LatexItemFactory renderer;
	private String dir = "item";
	
	@Before public void init(){renderer = new LatexItemFactory(cmm,dsm);}
	@After public void close(){renderer = null;}
	
    @Test public void itemize() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.itemize+".txt");
    	renderer.render(LatexListRenderer.ListType.list,ListItemProvider.build());
    	debugCharacter(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void descriptionNoItemName() throws IOException, OfxAuthoringException
    {
    	renderer.render(LatexListRenderer.ListType.description,ListItemProvider.build());
    }
    
    @Test
    public void description() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.description+".txt");
    	renderer.render(LatexListRenderer.ListType.description,ListItemProvider.description());
    	debugCharacter(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexItemFactory.initLoremIpsum();
    	TestLatexItemFactory test = new TestLatexItemFactory();
    	test.setEnvironment(true);
    	
    	test.init();test.itemize();
//    	test.init();test.description();
    }
}