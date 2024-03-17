package org.openfuxml.renderer.latex.structure;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openfuxml.doc.provider.old.MarginaliaProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractTestLatexRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLatexMarginaliaRenderer extends AbstractTestLatexRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexMarginaliaRenderer.class);

	private static enum Key {withParagraph,inParagraph}
	
	private LatexMarginaliaRenderer renderer;
    
	@Before public void init()
	{
		super.initDir("structure/marginalia");
		renderer = new LatexMarginaliaRenderer(cp);
	}
	@After public void close(){renderer=null;}
	
	@Test public void withParagraph() throws OfxAuthoringException, IOException
    {
		initFile(Key.withParagraph);
    	renderer.render(MarginaliaProvider.withParagraph());
    	renderTest(renderer);
    }
	
    @Test public void inParagraph() throws IOException, OfxAuthoringException
    {
    	initFile(Key.inParagraph);
    	LatexParagraphRenderer rendererParagraph = new LatexParagraphRenderer(cp,false); ;
    	rendererParagraph.render(MarginaliaProvider.inParagraph());
    	renderTest(rendererParagraph);
    }
	    
    public static void main(String[] args) throws Exception
    {
    	OfxBootstrap.init();
    	TestLatexMarginaliaRenderer.initLoremIpsum();
    	TestLatexMarginaliaRenderer test = new TestLatexMarginaliaRenderer();
    	test.setEnvironment(true);
        
    	test.init();test.withParagraph();
    	test.init();test.inParagraph();
    }
}