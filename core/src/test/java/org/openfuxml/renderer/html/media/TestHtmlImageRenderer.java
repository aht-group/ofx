package org.openfuxml.renderer.html.media;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlBody;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestHtmlImageRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlImageRenderer.class);

	private enum Key {inline,figure}
	
	private HtmlBody body;

	@Before public void init()
	{
		super.initDir("media");
		body = new HtmlBody(new OfxConfigurationProvider());
	}
	
	@Test
	public void fiugre() throws IOException
	{
		initFile(Key.figure);
		Section section = XmlSectionFactory.build(ImageProvider.figure("This is a figure"));
		JaxbUtil.info(section);
		body.render(section);
		renderTest(body);
	}
	
	@Test public void inline() throws IOException
	{
		initFile(Key.inline);
		Section sec = new Section(); sec.getContent().add(ImageProvider.inline("This would be an inline Image"));
		body.render(sec);
    	renderTest(body);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreBootstrap.init();
		TestHtmlImageRenderer test = new TestHtmlImageRenderer();
        test.setEnvironment(true);

		test.init();test.fiugre();
//        test.init();test.inline();

	}
}