package org.openfuxml.renderer.wiki;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWikiParagraphRenderer extends AbstractWikiContentTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestWikiParagraphRenderer.class);
	
	private static enum Key {plain}
	
	private WikiParagraphRenderer renderer;
	private String dir = "paragraph";
	
	@Before public void init(){renderer = new WikiParagraphRenderer(cmm,dsm);}
	@After public void close(){renderer=null;}
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
    @Test
    public void plain() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.plain+".txt");
    	renderer.render(create());
    	renderTest(renderer,f);
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