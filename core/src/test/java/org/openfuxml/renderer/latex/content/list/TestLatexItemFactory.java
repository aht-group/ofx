package org.openfuxml.renderer.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.doc.provider.old.ListItemProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexItemFactory extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexItemFactory.class);
	
	private static enum Key {itemize,description}
	private LatexItemFactory renderer;
	private String dir = "item";
	
	@Before public void init(){renderer = new LatexItemFactory(cp);}
	@After public void close(){renderer = null;}
	
    @Test public void itemize() throws IOException, OfxAuthoringException
    {
    	referenceFile = new File(rootDir,dir+"/"+Key.itemize+".txt");
    	renderer.render(LatexListRenderer.ListType.list,ListItemProvider.build());
    	debugCharacter(renderer);
    	super.save(renderer);
    	super.assertText(renderer);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void descriptionNoItemName() throws IOException, OfxAuthoringException
    {
    	renderer.render(LatexListRenderer.ListType.description,ListItemProvider.build());
    }
    
    @Test
    public void description() throws IOException, OfxAuthoringException
    {
    	referenceFile = new File(rootDir,dir+"/"+Key.description+".txt");
    	renderer.render(LatexListRenderer.ListType.description,ListItemProvider.description());
    	debugCharacter(renderer);
    	super.save(renderer);
    	super.assertText(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
			
    	TestLatexItemFactory.initLoremIpsum();
    	TestLatexItemFactory test = new TestLatexItemFactory();
    	test.setEnvironment(true);
    	
    	test.init();test.itemize();
//    	test.init();test.description();
    }
}