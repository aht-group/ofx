package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.doc.provider.old.ListingProvider;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlListingRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlListingRenderer.class);

	private enum Key {raw, external}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void raw() throws IOException
	{
		initFile(Key.raw);
        renderer.render(ListingProvider.build());
    	renderTest(renderer);
	}


	@Test public void external() throws IOException
	{
		initFile(Key.external);
		renderer.render(ListingProvider.buildEx());
		renderTest(renderer);
	}


	public static void main(String[] args) throws IOException
	{
		OfxBootstrap.init();
		TestHtmlListingRenderer test = new TestHtmlListingRenderer();
        test.setEnvironment(true);
		
       test.init();test.raw();
		test.init();test.external();
	}
}