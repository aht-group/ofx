package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlBodyRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlBodyRenderer.class);

	private enum Key {body, withComment}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void body() throws IOException
	{
		initFile(Key.body);
        renderer.render(SectionProvider.build());
    	renderTest(renderer);
	}

	@Test public void withComment() throws IOException
	{
		initFile(Key.withComment);
		renderer.render(SectionProvider.buildWithComment());
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlBodyRenderer test = new TestHtmlBodyRenderer();
        test.setEnvironment(true);
		
        test.init();test.body();
		test.init();test.withComment();
	}
}