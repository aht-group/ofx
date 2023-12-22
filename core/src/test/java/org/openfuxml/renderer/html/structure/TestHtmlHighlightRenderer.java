package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.old.MarginaliaProvider;
import org.openfuxml.model.xml.core.ofx.Highlight;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlHighlightRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlHighlightRenderer.class);

	private enum Key {highlight}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void highlight() throws IOException
	{
		initFile(Key.highlight);
		Section section = new Section();
		Highlight highlight = new Highlight();
		highlight.getContent().add(MarginaliaProvider.inParagraph());
		section.getContent().add(highlight);
        renderer.render(section);
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreBootstrap.init();
		TestHtmlHighlightRenderer test = new TestHtmlHighlightRenderer();
        test.setEnvironment(true);
		
        test.init();test.highlight();
	}
}