package org.openfuxml.renderer.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.content.SectionFactory;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.xml.content.list.List;
import org.openfuxml.xml.content.list.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexListFactory extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexListFactory.class);
	
	private static enum Key {ordered,unordered,description}
	private String dir = "list";
	
	private LatexListFactory renderer;
	private OfxLatexRenderer parentSection;
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexListFactory();
		parentSection = new SectionFactory(0,null);
	}
	
	@After public void close(){renderer=null;}
	
	public static List createList()
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List list = new List();
		list.setType(type);
		return list;
	}
	
    @Test(expected=OfxAuthoringException.class)
    public void withoutType() throws OfxAuthoringException, IOException
    {
    	List list = createList();
    	list.setType(null);
    	renderer.render(list,parentSection);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void noOrderingNoDescription() throws OfxAuthoringException, IOException
    {	
    	List list = createList();
    	list.getType().unsetDescription();
    	list.getType().setOrdering(null);
    	renderer.render(list,parentSection);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void noOrderingNoDescriptionFalse() throws OfxAuthoringException, IOException
    {	
    	List list = createList();
    	list.getType().setDescription(false);
    	list.getType().setOrdering(null);
    	renderer.render(list,parentSection);
    }
    
    
    @Test(expected=OfxAuthoringException.class)
    public void descriptionButOrdering() throws OfxAuthoringException, IOException
    {	
    	List list = createList();
    	list.getType().setDescription(true);
    	list.getType().setOrdering("x");
    	renderer.render(list,parentSection);
    }
      
    @Test
    public void unordered() throws OfxAuthoringException, IOException
    {	
    	f = new File(rootDir,dir+"/"+Key.unordered+".txt");
    	List list = createList();
    	list.getItem().add(TestLatexItemFactory.createItem());
    	renderer.render(list,parentSection);
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Test
    public void ordered() throws OfxAuthoringException, IOException
    {	
    	f = new File(rootDir,dir+"/"+Key.ordered+".txt");
    	List list = createList();
    	list.getType().setOrdering("ordered");
    	list.getItem().add(TestLatexItemFactory.createItem());
    	renderer.render(list,parentSection);
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
    
    @Ignore("Description NYI")
    @Test
    public void description() throws OfxAuthoringException, IOException
    {	
    	f = new File(rootDir,dir+"/"+Key.description+".txt");
    	List list = createList();
    	list.getType().setDescription(true);
    	list.getType().setOrdering(null);
    	list.getItem().add(TestLatexItemFactory.createItem(li.getWords(1)));
    	renderer.render(list,parentSection);
    	debug(renderer);
    	save(renderer,f);
    	assertText(renderer,f);
    }
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexListFactory.initLoremIpsum();
    	TestLatexListFactory test = new TestLatexListFactory();
    	test.setSaveReference(true);
    	
//    	test.initRenderer();test.unordered();   	
//    	test.initRenderer();test.ordered();
    	test.initRenderer();test.description();
    	
    }
   
}