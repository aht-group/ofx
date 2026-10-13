package org.openfuxml.renderer.text.structure;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.controller.provider.text.ParagraphProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.text.AbstractTestTextRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTextParagraphRenderer extends AbstractTestTextRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestTextParagraphRenderer.class);
	
	private static enum Key {simple}
	
	private TextParagraphRenderer renderer;
    
	@BeforeEach public void init()
	{
		super.initDir("content/paragraph");
		renderer = new TextParagraphRenderer(cp);
	}
	@AfterEach public void close() {renderer = null;}
	
    @Test
    public void simple() throws IOException, OfxAuthoringException
    {
    	initFile(Key.simple);
    	renderer.render(ParagraphProvider.create());
    	renderTest(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
    	TestTextParagraphRenderer test = new TestTextParagraphRenderer();
    	test.setEnvironment(true);
    	
    	test.init();test.simple();
    }
}