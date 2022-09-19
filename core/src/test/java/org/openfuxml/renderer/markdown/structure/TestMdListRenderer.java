package org.openfuxml.renderer.markdown.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.controller.provider.list.ListProvider;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMdListRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdListRenderer.class);

	private enum Key {ordered, unordered}
	
	private MdListRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure/list");
		renderer = new MdListRenderer(cp);
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
		OfxCoreBootstrap.init();
		TestMdListRenderer test = new TestMdListRenderer();
        test.setEnvironment(true);
		
        test.init();test.ordered();
        test.init();test.unordered();
	}
}