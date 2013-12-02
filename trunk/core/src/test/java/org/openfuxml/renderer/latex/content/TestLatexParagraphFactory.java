package org.openfuxml.renderer.latex.content;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.LatexParagraphFactory;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexParagraphFactory extends AbstractLatexContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphFactory.class);
	
	private static enum Key {withBlank,withoutBlank}
	
	private LatexParagraphFactory renderer;
	private String dir = "paragraph";
	
	@After public void close(){renderer=null;}
	
	public static Paragraph create()
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(10));
    	return p;
	}
	
    @Test
    public void withBlank() throws IOException, OfxAuthoringException
    {
    	renderer = new LatexParagraphFactory(true);
    	
    	f = new File(rootDir,dir+"/"+Key.withBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    @Test
    public void withoutBlank() throws IOException, OfxAuthoringException
    {
    	renderer = new LatexParagraphFactory(false);
    	
    	f = new File(rootDir,dir+"/"+Key.withoutBlank+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexParagraphFactory.initLoremIpsum();
    	TestLatexParagraphFactory test = new TestLatexParagraphFactory();
    	test.setSaveReference(true);
    	
 //   	test.withBlank();
    	test.withoutBlank();
    }
   
}