package org.openfuxml.renderer.html.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.MarginaliaProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
		OfxCoreTestBootstrap.init();
		TestHtmlHighlightRenderer test = new TestHtmlHighlightRenderer();
        test.setEnvironment(true);
		
        test.init();test.highlight();
	}
}