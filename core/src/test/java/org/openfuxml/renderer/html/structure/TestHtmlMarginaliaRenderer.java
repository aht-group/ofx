package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
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
		renderer = new HtmlBody(new OfxConfigurationProvider());
	}

	@Test public void marginalia() throws IOException
	{
		initFile(Key.marginalia);
		Section section = new Section();
		section.getContent().add(MarginaliaProvider.inParagraph());
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