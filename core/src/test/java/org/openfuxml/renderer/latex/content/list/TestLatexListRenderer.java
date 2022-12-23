package org.openfuxml.renderer.latex.content.list;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.doc.provider.old.ListItemProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlSpacingFactory;
import org.openfuxml.factory.xml.ofx.layout.XmlLayoutFactory;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestLatexListRenderer extends AbstractLatexListTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexListRenderer.class);
	
	private static enum Key {ordered,unordered,description,layout}
	private String dir = "list";
	
	private LatexListRenderer renderer;
	private OfxLatexRenderer parentSection;
	
	@Before
	public void initRenderer()
	{
		renderer = new LatexListRenderer(cp);
		parentSection = new LatexSectionRenderer(cp,0,null);
	}
	
	@After public void close(){renderer=null;}
	
	public static List createList(){return createList(0);}
	public static List createList(int size)
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List list = new List();
		list.setType(type);
		for(int i=0;i<size;i++){list.getItem().add(ListItemProvider.build());}
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
    public void layout() throws OfxAuthoringException, IOException
    {	
    	referenceFile = new File(rootDir,dir+"/"+Key.unordered+".txt");
    	List list = createList(2);
    	list.setLayout(XmlLayoutFactory.build(XmlSpacingFactory.pt(0)));

    	JaxbUtil.info(list);
    	renderer.render(list,parentSection);
    	debugCharacter(renderer);
    	super.save(renderer);
    	assertText(renderer);
    }
    
    @Test
    public void unordered() throws OfxAuthoringException, IOException
    {	
    	referenceFile = new File(rootDir,dir+"/"+Key.layout+".txt");
    	List list = createList();
    	list.getItem().add(ListItemProvider.build());
    	JaxbUtil.info(list);
    	renderer.render(list,parentSection);
    	debugCharacter(renderer);
    	super.save(renderer);
    	super.assertText(renderer);
    }
    
    @Test
    public void ordered() throws OfxAuthoringException, IOException
    {	
    	referenceFile = new File(rootDir,dir+"/"+Key.ordered+".txt");
    	List list = createList();
    	list.getType().setOrdering("ordered");
    	list.getItem().add(ListItemProvider.build());
    	JaxbUtil.info(list);
    	renderer.render(list,parentSection);
    	debugCharacter(renderer);
    	super.save(renderer);
    	super.assertText(renderer);
    }
    
    @Ignore("Description NYI")
    @Test
    public void description() throws OfxAuthoringException, IOException
    {	
    	referenceFile = new File(rootDir,dir+"/"+Key.description+".txt");
    	List list = createList();
    	list.getType().setDescription(true);
    	list.getType().setOrdering(null);
    	list.getItem().add(ListItemProvider.description());
    	renderer.render(list,parentSection);
    	debugCharacter(renderer);
    	save(renderer);
    	assertText(renderer);
    }
  
    public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();
			
    	TestLatexListRenderer.initLoremIpsum();
    	TestLatexListRenderer test = new TestLatexListRenderer();
    	test.setEnvironment(true);
    	
//    	test.initRenderer();test.unordered();
//    	test.initRenderer();test.ordered();
//    	test.initRenderer();test.description();
    	test.initRenderer();test.layout();
    }
}