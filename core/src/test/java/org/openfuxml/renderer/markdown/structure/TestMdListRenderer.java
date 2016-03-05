package org.openfuxml.renderer.markdown.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ListProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdListRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdListRenderer.class);

	private enum Key {ordered, unordered}
	
	private MdListRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure/list");
		renderer = new MdListRenderer(new OfxConfigurationProvider());
	}

	@Test public void ordered() throws IOException
	{
		initFile(Key.ordered);
        renderer.render(ListProvider.build(true));
    	renderTest(renderer);
	}
	
	@Test public void unordered() throws IOException
	{
		initFile(Key.unordered);
        renderer.render(ListProvider.build(false));
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