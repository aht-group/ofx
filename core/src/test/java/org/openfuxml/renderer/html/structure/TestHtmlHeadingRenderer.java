package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlHeadingRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHeadingRenderer.class);

	private enum Key {lvl1,lvl2}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void lvl1() throws IOException
	{
		initFile(Key.lvl1);

        renderer.render(SectionProvider.build());
    	renderTest(renderer);
	}
	
	@Test public void lvl2() throws IOException
	{
		initFile(Key.lvl2);
		Section section = new Section(); section.getContent().add(SectionProvider.build());
		renderer.render(section);
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