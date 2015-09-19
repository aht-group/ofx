package org.openfuxml.renderer.latex.content.editorial;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.editorial.Acronyms;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.editorial.XmlTermFactory;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.renderer.latex.content.AbstractLatexContentTest;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.util.OfxCommentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexAcronymRenderer extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private LatexAcronymRenderer renderer;
	private String dir = "editorial/acronyms";
	
	@Before public void init()
	{
		renderer = new LatexAcronymRenderer(cmm,dsm);
	}
	@After public void close()
	{
		renderer=null;
	}
	
	public static Acronyms create()
	{
		Acronyms g = new Acronyms();
    	g.getTerm().add(XmlTermFactory.build("c1", "Code-1", "Description-1"));
    	g.getTerm().add(XmlTermFactory.build("c2", "Code-2", "Description-2"));
		
		Comment comment = XmlCommentFactory.build();
		OfxCommentBuilder.doNotModify(comment);
		g.setComment(comment);
    	
    	return g;
	}
	
    @Test public void acronyms() throws IOException, OfxAuthoringException
    {    	
    	f = new File(rootDir,dir+"/acronyms.txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void termWithoutCode() throws OfxAuthoringException
    {    	
    	Acronyms g = create();
    	g.getTerm().add(XmlTermFactory.build(null, "name", "description"));
    	renderer.render(g);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void termWithoutText() throws OfxAuthoringException
    {    	
    	Acronyms g = create();
    	g.getTerm().add(XmlTermFactory.build("code", null, "description"));
    	renderer.render(g);
    }
    
    @Test(expected=OfxAuthoringException.class)
    public void termWithoutParagraph() throws OfxAuthoringException
    {    	
    	Acronyms g = create();
    	g.getTerm().add(XmlTermFactory.build("code", "name", null));
    	renderer.render(g);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexAcronymRenderer.initLoremIpsum();
    	TestLatexAcronymRenderer test = new TestLatexAcronymRenderer();
    	test.init();
    	test.setSaveReference(true);
    	test.acronyms();
    }
}