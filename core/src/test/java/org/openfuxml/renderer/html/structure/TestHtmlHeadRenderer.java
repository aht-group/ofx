package org.openfuxml.renderer.html.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.head.HtmlHead;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionAndTitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestHtmlHeadRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHeadRenderer.class);

	private enum Key {head}
	
	private HtmlHead renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlHead(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void head() throws IOException
	{
		initFile(Key.head);
        renderer.render();
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlHeadRenderer test = new TestHtmlHeadRenderer();
        test.setEnvironment(true);
		
        test.init();test.head();
	}
}