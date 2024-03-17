package org.openfuxml.renderer.markdown.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.controller.provider.text.EmphasisProvider;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMdEmphasisRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdEmphasisRenderer.class);

	private enum Key {italic, bold, quote, typewriter, combination}
	
	private MdParagraphRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure/emphasis");
		renderer = new MdParagraphRenderer(cp);
	}

	@Test public void italic() throws IOException
	{
		initFile(Key.italic);
        renderer.render(EmphasisProvider.italic());
    	renderTest(renderer);
	}
	
	@Test public void bold() throws IOException
	{
		initFile(Key.bold);
        renderer.render(EmphasisProvider.bold());
    	renderTest(renderer);
	}
	@Test public void quote() throws IOException
	{
		initFile(Key.quote);
		renderer.render(EmphasisProvider.quote());
		renderTest(renderer);
	}

	@Test public void combination() throws IOException
	{
		initFile(Key.combination);
		renderer.render(EmphasisProvider.italicBold());
		renderTest(renderer);
	}
	@Test public void typewriter() throws IOException
	{
		initFile(Key.typewriter);
		renderer.render(EmphasisProvider.typewriter());
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxBootstrap.init();
		TestMdEmphasisRenderer test = new TestMdEmphasisRenderer();
        test.setEnvironment(true);
		
        test.init();test.bold();
        test.init();test.italic();
		test.init();test.combination();
		test.init();test.quote();
		test.init();test.typewriter();
	}
}