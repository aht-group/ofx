package org.openfuxml.renderer.wiki;

import java.io.IOException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Initial Test
 * @author yannkruger
 *
 */
public class TestWikiParagraphRenderer extends AbstractTestWikiRenderer 
{	
	final static Logger logger = LoggerFactory.getLogger(TestWikiParagraphRenderer.class);
	
	private enum Key {plain}
	
	private WikiParagraphRenderer renderer;
	
	@BeforeEach public void init(){renderer = new WikiParagraphRenderer(cp);}
	@AfterEach public void close(){renderer=null;}
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
	//TODO FIX
	@Disabled
    @Test
    public void plain() throws IOException, OfxAuthoringException
    {
    	initFile(Key.plain);
        renderer.render(create());
    	renderTest(renderer);
    }
    
    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
			
    	TestWikiParagraphRenderer.initLoremIpsum();
    	TestWikiParagraphRenderer test = new TestWikiParagraphRenderer();
    	test.init();
    	test.setEnvironment(true);
    	
    	test.plain();
    }
}