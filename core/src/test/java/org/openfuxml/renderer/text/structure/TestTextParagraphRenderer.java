package org.openfuxml.renderer.text.structure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.text.ParagraphProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.text.AbstractTestTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTextParagraphRenderer extends AbstractTestTextRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestTextParagraphRenderer.class);
	
	private static enum Key {simple}
	
	private TextParagraphRenderer renderer;
    
	@Before public void init()
	{
		super.initDir("content/paragraph");
		renderer = new TextParagraphRenderer(cp);
	}
	@After public void close() {renderer = null;}
	
    @Test
    public void simple() throws IOException, OfxAuthoringException
    {
    	initFile(Key.simple);
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreBootstrap.init();
    	TestTextParagraphRenderer test = new TestTextParagraphRenderer();
    	test.setEnvironment(true);
    	
    	test.init();test.simple();
    }
}