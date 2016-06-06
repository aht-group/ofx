package org.openfuxml.renderer.html.media;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlBody;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ImageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlImageRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlImageRenderer.class);

	private enum Key {inline, only}
	
	private HtmlBody body;

	@Before public void init()
	{
		super.initDir("media");
		body = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void inline() throws IOException
	{
		initFile(Key.inline);
		Section sec = new Section(); sec.getContent().add(ImageProvider.create("This would be an inline Image"));
		body.render(sec);
    	renderTest(body);
	}
	
	@Test @Ignore
	public void only() throws IOException
	{
		initFile(Key.only);
		Section sec = new Section(); sec.getContent().add(ImageProvider.createImageOnly("This would be an Image"));
		body.render(sec);
		renderTest(body);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlImageRenderer test = new TestHtmlImageRenderer();
        test.setEnvironment(true);

		test.init();test.only();
        test.init();test.inline();

	}
}