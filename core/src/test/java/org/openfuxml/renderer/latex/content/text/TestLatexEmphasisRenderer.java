package org.openfuxml.renderer.latex.content.text;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.content.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.EmphasisProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexEmphasisRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	private static enum Key {bold,italic,quote}
	private static String dir = "emphasis";
	
	private LatexParagraphRenderer renderer;
	
	@Before
	public void init()
	{
		renderer = new LatexParagraphRenderer(cmm,dsm,true);
	}
	
	@After public void close(){renderer=null;}
	
    @Test
    public void bold() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.bold+".txt");
    	renderer.render(EmphasisProvider.bold());
    	renderTest(renderer,f);
    }
    
    @Test public void italic() throws IOException, OfxAuthoringException
    {    	
    	f = new File(rootDir,dir+"/"+Key.italic+".txt");
    	renderer.render(EmphasisProvider.italic());
    	renderTest(renderer,f);
    }
    
    @Test public void quote() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.quote+".txt");
    	renderer.render(EmphasisProvider.italic());
    	renderTest(renderer,f);
    }
    
    @Test
    public void italicBold() throws IOException, OfxAuthoringException
    {
    	f = new File(rootDir,dir+"/"+Key.italic+"-"+Key.bold+".txt");
    	renderer.render(EmphasisProvider.italicBold());
    	renderTest(renderer,f);
    }
    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
			
    	TestLatexEmphasisRenderer test = new TestLatexEmphasisRenderer();
    	
    	test.setEnvironment(true);
    	
    	test.init();test.bold();
    	test.init();test.italic();
    	test.init();test.quote();
    	test.init();test.italicBold();
    }
}