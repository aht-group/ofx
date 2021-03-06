package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.doc.provider.text.EmphasisProvider;
import org.openfuxml.doc.provider.text.SectionProvider;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlEmphasisRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlEmphasisRenderer.class);

	private enum Key {emphasis}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void emphasis() throws IOException
	{
		initFile(Key.emphasis);
		Section section = SectionProvider.build();
		section.getContent().add(EmphasisProvider.bold());
		section.getContent().add(EmphasisProvider.italic());
		section.getContent().add(EmphasisProvider.italicBold());
		section.getContent().add(EmphasisProvider.typewriter());
		section.getContent().add(EmphasisProvider.quote());
		section.getContent().add(EmphasisProvider.underline());
        renderer.render(section);
    	renderTest(renderer);
	}
	
	public static void main(String[] args) throws IOException
	{
		OfxCoreBootstrap.init();
		TestHtmlEmphasisRenderer test = new TestHtmlEmphasisRenderer();
        test.setEnvironment(true);
		
        test.init();test.emphasis();
	}
}