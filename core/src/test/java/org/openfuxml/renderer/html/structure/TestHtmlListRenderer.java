package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ListProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlListRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlListRenderer.class);

	private enum Key {ordered, unordered, description}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void ordered() throws IOException
	{
		initFile(Key.ordered);
		Section s = new Section(); s.getContent().add(ListProvider.build(true));
        renderer.render(s);
    	renderTest(renderer);
	}

	@Test public void unordered() throws IOException
	{
		initFile(Key.unordered);
		Section s = new Section(); s.getContent().add(ListProvider.build(false));
		renderer.render(s);
		renderTest(renderer);
	}

	@Test public void description() throws IOException
	{
		initFile(Key.description);
		Section s = new Section(); s.getContent().add(ListProvider.buildDescription());
		renderer.render(s);
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlListRenderer test = new TestHtmlListRenderer();
        test.setEnvironment(true);
		
        test.init();test.ordered();
		test.init();test.unordered();
		test.init();test.description();
	}
}