package org.openfuxml.renderer.html.media;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.media.cross.HtmlCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlBody;
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
		OfxConfigurationProvider cp = new OfxConfigurationProvider();
		cp.setCrossMediaManager(new HtmlCrossMediaManager());
		
		body = new HtmlBody(cp);
	}
	
	@Test public void dummy() {}
	
//	@Test
	public void figure() throws IOException
	{
		initFile(Key.figure);
		Section section = XmlSectionFactory.build(ImageProvider.figure("This is a figure"));
		JaxbUtil.debug(section);
		body.render(section);
		super.renderTest(body);
	}
	
//	@Test
	public void inline() throws IOException
	{
		initFile(Key.inline);
		Section section = XmlSectionFactory.build(ImageProvider.inline("This would be an inline Image"));
		JaxbUtil.debug(section);
		body.render(section);
		super.renderTest(body);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreBootstrap.init();
		TestHtmlImageRenderer test = new TestHtmlImageRenderer();
        test.setEnvironment(true);

		test.init();test.figure();
        test.init();test.inline();

	}
}