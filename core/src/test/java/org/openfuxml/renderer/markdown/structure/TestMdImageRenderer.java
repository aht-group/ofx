package org.openfuxml.renderer.markdown.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.renderer.markdown.AbstractTestMdRenderer;
import org.openfuxml.renderer.markdown.media.MdImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMdImageRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdImageRenderer.class);

	private enum Key {inline, only}
	
	private MdImageRenderer renderer;
	private MdParagraphRenderer pRenderer;

	@Before public void init()
	{
		super.initDir("media");
		renderer = new MdImageRenderer(cp);
		pRenderer = new MdParagraphRenderer(cp);
	}

	@Test public void inline() throws IOException
	{
		initFile(Key.inline);
        pRenderer.render(ImageProvider.inline("This would be an inline Image"));
    	renderTest(pRenderer);
	}
	
	@Test public void only() throws IOException
	{
		initFile(Key.only);
        renderer.render(ImageProvider.figure("This would be an Image outside a paragraph"));
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreBootstrap.init();
		TestMdImageRenderer test = new TestMdImageRenderer();
        test.setEnvironment(true);
		
        test.init();test.inline();
        test.init();test.only();
	}
}