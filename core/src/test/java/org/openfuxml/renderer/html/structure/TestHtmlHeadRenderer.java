package org.openfuxml.renderer.html.structure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlHeadRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHeadRenderer.class);

	private enum Key {head, cssHead}
	
	private HtmlHead renderer;

	@Before public void init()
	{
		super.initDir("head");
		renderer = new HtmlHead(new OfxConfigurationProvider());
	}

	@Test public void head() throws IOException
	{
		initFile(Key.head);
        renderer.render(null, "");
    	renderTest(renderer);
	}
	@Test public void cssHead() throws IOException
	{
		initFile(Key.cssHead);
		List<String> cssFiles = new ArrayList<String>();
		cssFiles.add("pseudo.css");
		renderer.render(cssFiles,"");
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlHeadRenderer test = new TestHtmlHeadRenderer();
        test.setEnvironment(true);
		
        test.init();test.head();
		test.init();test.cssHead();
	}
}