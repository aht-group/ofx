package org.openfuxml.renderer.markdown.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.TitleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdSectionRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdSectionRenderer.class);

	private enum Key {lvl1,lvl2}
	
	private MdSectionRenderer renderer;
	int lvl;

	@Before public void init()
	{
		super.initDir("structure/section");
	}

	@Test public void lvl1() throws IOException
	{
		lvl = 1;
		renderer = new MdSectionRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager(), lvl);
		initFile(Key.lvl1);
        renderer.render(TitleProvider.build());
    	renderTest(renderer);
	}
	
	@Test public void lvl2() throws IOException
	{
		lvl = 2;
		renderer = new MdSectionRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager(), lvl);
		initFile(Key.lvl2);
        renderer.render(TitleProvider.build());
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestMdSectionRenderer test = new TestMdSectionRenderer();
        test.setEnvironment(true);
		
        test.init();test.lvl1();
        test.init();test.lvl2();
	}
}