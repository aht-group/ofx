package org.openfuxml.renderer.markdown.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ListProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdListRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdListRenderer.class);

	private enum Key {ordered, unordered, nested}
	
	private MdListRenderer renderer;
	private OfxMdRenderer parent;

	@Before public void init()
	{
		super.initDir("structure/list");
		renderer = new MdListRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
		parent = new MdSectionRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager(),1);
	}

	@Test public void ordered() throws IOException
	{
		initFile(Key.ordered);
        renderer.render(ListProvider.description(true), parent);
    	renderTest(renderer);
	}
	
	@Test public void unordered() throws IOException
	{
		initFile(Key.unordered);
        renderer.render(ListProvider.description(false), parent);
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestMdListRenderer test = new TestMdListRenderer();
        test.setEnvironment(true);
		
        test.init();test.ordered();
        test.init();test.unordered();
	}
}