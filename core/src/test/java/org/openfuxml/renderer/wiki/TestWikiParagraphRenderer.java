package org.openfuxml.renderer.wiki;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
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
	
	@Before public void init(){renderer = new WikiParagraphRenderer(cmm,dsm);}
	@After public void close(){renderer=null;}
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
	//TODO FIX
	@Ignore
    @Test
    public void plain() throws IOException, OfxAuthoringException
    {
    	initFile(Key.plain);
        renderer.render(create());
    	renderTest(renderer);
    }
    
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestWikiParagraphRenderer.initLoremIpsum();
    	TestWikiParagraphRenderer test = new TestWikiParagraphRenderer();
    	test.init();
    	test.setEnvironment(true);
    	
    	test.plain();
    }
}