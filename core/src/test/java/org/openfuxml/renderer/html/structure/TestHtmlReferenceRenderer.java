package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openfuxml.controller.provider.text.ParagraphProvider;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.openfuxml.model.xml.core.ofx.Reference;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlReferenceRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlReferenceRenderer.class);

	private enum Key {intern, extern}
	
	private HtmlBody renderer;

	@BeforeEach public void init()
	{
		super.initDir("reference");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}
	
	private Reference buildReference(String target)
	{
		Reference ref = new Reference();
		ref.setTarget(target);
		ref.setValue(target);
		return ref;
	}

	@Test @Disabled public void intern() throws IOException
	{
		initFile(Key.intern);
		Section s = new Section ();
		Paragraph p = ParagraphProvider.create(); p.getContent().add(buildReference("table.example.target"));
		s.getContent().add(p);
        renderer.render(s);
    	renderTest(renderer);
	}

	@Test /*@Disabled*/
	public void extern() throws IOException
	{
		initFile(Key.extern);
		Section s = new Section ();
		Reference r = buildReference("www.google.com");
		r.setType("external");
		Paragraph p = ParagraphProvider.create(); p.getContent().add(r);
		s.getContent().add(p);
		renderer.render(s);
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxBootstrap.init();
		TestHtmlReferenceRenderer test = new TestHtmlReferenceRenderer();
        test.setEnvironment(true);
		
        test.init();test.intern();
		test.init();test.extern();
	}
}