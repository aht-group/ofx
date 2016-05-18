package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlReferenceRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlReferenceRenderer.class);

	private enum Key {intern, extern}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("reference");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}
	
	private Reference buildReference(String target)
	{
		Reference ref = new Reference();
		ref.setTarget(target);
		return ref;
	}

	@Test public void intern() throws IOException
	{
		initFile(Key.intern);
		Section s = new Section ();
		Paragraph p = ParagraphProvider.create(); p.getContent().add(buildReference("table.example.target"));
		s.getContent().add(p);
        renderer.render(s);
    	renderTest(renderer);
	}

	@Test @Ignore
	public void extern() throws IOException
	{
		initFile(Key.extern);
		Section s = new Section ();
		Paragraph p = ParagraphProvider.create(); p.getContent().add(buildReference("www.google.de"));
		s.getContent().add(p);
		renderer.render(s);
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlReferenceRenderer test = new TestHtmlReferenceRenderer();
        test.setEnvironment(true);
		
        test.init();test.intern();
		test.init();test.extern();
	}
}