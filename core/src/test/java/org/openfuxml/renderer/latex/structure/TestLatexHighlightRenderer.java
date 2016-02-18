package org.openfuxml.renderer.latex.structure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexHighlightRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.HighlightProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexHighlightRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexHighlightRenderer.class);

	private static enum Key {simple}
	
	private LatexHighlightRenderer renderer;
    
	@Before public void init()
	{
		super.initDir("structure/highlight");
		renderer = new LatexHighlightRenderer(cmm,dsm);
	}
	@After public void close(){renderer=null;}
    
	@Test public void highlight() throws OfxAuthoringException, OfxConfigurationException, IOException
    {
		initFile(Key.simple);
    	renderer.render(HighlightProvider.simple());
    	renderTest(renderer);
    }
	    
    public static void main(String[] args) throws Exception
    {
    	OfxCoreTestBootstrap.init();
    	TestLatexHighlightRenderer.initLoremIpsum();
    	TestLatexHighlightRenderer test = new TestLatexHighlightRenderer();
    	test.setEnvironment(true);
        
    	test.init();
       	test.highlight();
    }
}