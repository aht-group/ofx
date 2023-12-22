package org.openfuxml.renderer.html.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.old.MarginaliaProvider;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.AbstractTestHtmlRenderer;
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
		OfxCoreBootstrap.init();
		TestHtmlMarginaliaRenderer test = new TestHtmlMarginaliaRenderer();
        test.setEnvironment(true);
		
        test.init();test.marginalia();
	}
}