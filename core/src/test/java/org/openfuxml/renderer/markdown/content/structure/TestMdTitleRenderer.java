package org.openfuxml.renderer.markdown.content.structure;


import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.renderer.markdown.structure.MdTitleRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.TitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMdTitleRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdTitleRenderer.class);

	private static enum Key {lvl1,lvl2}
	
	private MdTitleRenderer renderer;

	@Before
	void init()
	{
		super.initDir("structure/title");
		renderer = new MdTitleRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test void lvl1() throws IOException
	{
		initFile(Key.lvl1);
        renderer.render(TitleProvider.create(),1);
    	renderTest(renderer);
	}
	
	@Test void lvl2() throws IOException
	{
		initFile(Key.lvl2);
        renderer.render(TitleProvider.create(),2);
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestMdTitleRenderer test = new TestMdTitleRenderer();
        test.setEnvironment(true);
		
        test.init();test.lvl1();
        test.init();test.lvl2();
	}
}