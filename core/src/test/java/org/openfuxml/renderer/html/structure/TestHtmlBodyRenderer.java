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

public class TestHtmlBodyRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlBodyRenderer.class);

	private enum Key {body, withComment}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void body() throws IOException
	{
		initFile(Key.body);
        renderer.render(SectionAndTitleProvider.build(),1);
    	renderTest(renderer);
	}

	@Test public void withComment() throws IOException
	{
		initFile(Key.withComment);
		renderer.render(SectionAndTitleProvider.buildWithComment(),1);
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