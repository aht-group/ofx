package org.openfuxml.renderer.markdown.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.EmphasisProvider;
import org.openfuxml.test.provider.ListProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdEmphasisRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdEmphasisRenderer.class);

	private enum Key {italic, bold, quote, typewriter, combination}
	
	private MdParagraphRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure/emphasis");
		renderer = new MdParagraphRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
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
		OfxCoreTestBootstrap.init();
		TestMdEmphasisRenderer test = new TestMdEmphasisRenderer();
        test.setEnvironment(true);
		
        test.init();test.bold();
        test.init();test.italic();
		test.init();test.combination();
		test.init();test.quote();
		test.init();test.typewriter();
	}
}