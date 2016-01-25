package org.openfuxml.renderer.text.structure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.AbstractOfxCoreTest;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTextParagraphRenderer extends AbstractOfxCoreTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestTextParagraphRenderer.class);
	
	private TextParagraphRenderer renderer;
    
	@Before public void init() {renderer = new TextParagraphRenderer(cmm,dsm);}
	@After public void close() {renderer = null;}
	
    @Test
    public void test() throws IOException, OfxAuthoringException
    {
    	renderer.render(ParagraphProvider.create());
    	debugCharacter(renderer);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestTextParagraphRenderer.initLoremIpsum();
    	TestTextParagraphRenderer cli = new TestTextParagraphRenderer();
    	cli.init();
    	
    	cli.test();
    }
}