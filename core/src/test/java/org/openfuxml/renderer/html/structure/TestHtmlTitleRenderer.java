package org.openfuxml.renderer.html.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.renderer.markdown.structure.MdTitleRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionAndTitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestHtmlTitleRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlTitleRenderer.class);

	private enum Key {lvl1,lvl2}
	
	private HtmlTitleRenderer renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlTitleRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
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
		TestHtmlTitleRenderer test = new TestHtmlTitleRenderer();
        test.setEnvironment(true);
		
        test.init();test.lvl1();
        test.init();test.lvl2();
	}
}