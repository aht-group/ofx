package org.openfuxml.renderer.html.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionAndTitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestHtmlHeadingRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHeadingRenderer.class);

	private enum Key {lvl1,lvl2}
	
	private HtmlHeadingRenderer renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlHeadingRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void lvl1() throws IOException
	{
		initFile(Key.lvl1);
        renderer.render(SectionAndTitleProvider.create(),1);
    	renderTest(renderer);
	}
	
	@Test public void lvl2() throws IOException
	{
		initFile(Key.lvl2);
        renderer.render(SectionAndTitleProvider.create(),2);
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlHeadingRenderer test = new TestHtmlHeadingRenderer();
        test.setEnvironment(true);
		
        test.init();test.lvl1();
        test.init();test.lvl2();
	}
}