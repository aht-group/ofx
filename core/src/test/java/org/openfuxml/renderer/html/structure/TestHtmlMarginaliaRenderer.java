package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.processor.settings.OfxDefaultSettingsManager;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.MarginaliaProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtmlMarginaliaRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlMarginaliaRenderer.class);

	private enum Key {marginalia}
	
	private HtmlBody renderer;

	@Before public void init()
	{
		super.initDir("section");
		renderer = new HtmlBody(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void marginalia() throws IOException
	{
		initFile(Key.marginalia);
		Section section = new Section();
		section.getContent().add(MarginaliaProvider.withParagraph());
        renderer.render(section);
    	renderTest(renderer);
	}

	public static void main(String[] args) throws IOException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlMarginaliaRenderer test = new TestHtmlMarginaliaRenderer();
        test.setEnvironment(true);
		
        test.init();test.marginalia();
	}
}