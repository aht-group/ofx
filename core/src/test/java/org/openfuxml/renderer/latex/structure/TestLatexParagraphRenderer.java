package org.openfuxml.renderer.latex.structure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.doc.provider.text.ParagraphProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFontFactory;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexParagraphRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private static enum Key {withBlank,withoutBlank,marginalia,fontRelative}
	
	private LatexParagraphRenderer renderer;
	
	@Before public void init()
	{
		super.initDir("content/paragraph");
		renderer = new LatexParagraphRenderer(cp,false);
	}
	@After public void close(){renderer=null;}
		
    @Test
    public void withBlank() throws IOException, OfxAuthoringException
    {
    	initFile(Key.withBlank);
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer);
    }
    
    @Test @Ignore
    public void withoutBlank() throws IOException, OfxAuthoringException
    {
    	initFile(Key.withoutBlank);
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer);
    }
        
    @Test
    public void fontRelative() throws IOException, OfxAuthoringException
    {
    	initFile(Key.fontRelative);
    	Paragraph p = ParagraphProvider.create();
    	p.getContent().add(XmlFontFactory.relative(-2));
    	renderer.render(p);
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();
    	TestLatexParagraphRenderer.initLoremIpsum();
    	TestLatexParagraphRenderer test = new TestLatexParagraphRenderer();
    	test.setEnvironment(true);
    	
    	test.init();test.withBlank();
    	test.init();test.withoutBlank();
    	test.init();test.fontRelative();
    }
}