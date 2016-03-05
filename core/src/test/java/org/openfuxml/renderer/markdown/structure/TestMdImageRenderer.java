package org.openfuxml.renderer.markdown.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.renderer.markdown.media.MdImageRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.EmphasisProvider;
import org.openfuxml.test.provider.ImageProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdImageRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdImageRenderer.class);

	private enum Key {inline, only}
	
	private MdImageRenderer renderer;
	private MdParagraphRenderer pRenderer;

	@Before public void init()
	{
		super.initDir("media");
		renderer = new MdImageRenderer(new OfxConfigurationProvider());
		pRenderer = new MdParagraphRenderer(new OfxConfigurationProvider());
	}

	@Test public void inline() throws IOException
	{
		initFile(Key.inline);
        pRenderer.render(ImageProvider.create("This would be an inline Image"));
    	renderTest(pRenderer);
	}
	
	@Test public void only() throws IOException
	{
		initFile(Key.only);
        renderer.render(ImageProvider.createImageOnly("This would be an Image outside a paragraph"));
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestMdImageRenderer test = new TestMdImageRenderer();
        test.setEnvironment(true);
		
        test.init();test.inline();
        test.init();test.only();
	}
}