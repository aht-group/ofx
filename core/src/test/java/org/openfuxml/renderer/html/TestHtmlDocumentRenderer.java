package org.openfuxml.renderer.html;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.html.structure.HtmlBody;
import org.openfuxml.renderer.html.structure.HtmlDocumentRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.DocumentProvider;
import org.openfuxml.test.provider.SectionProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestHtmlDocumentRenderer extends AbstractTestHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestHtmlDocumentRenderer.class);

	private enum Key {doc, withSub}
	
	private HtmlDocumentRenderer renderer;

	@Before public void init()
	{
		super.initDir("");
		renderer = new HtmlDocumentRenderer(new OfxConfigurationProvider(), "Test Doc");
	}

	@Test public void doc() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.doc);
        renderer.render(DocumentProvider.build().getContent());
    	renderTest(renderer);
	}

	@Test public void withSub() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.withSub);
		renderer.render(DocumentProvider.buildWithSubcontent().getContent());
		renderTest(renderer);
	}

	public static void main(String[] args) throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		OfxCoreTestBootstrap.init();
		TestHtmlDocumentRenderer test = new TestHtmlDocumentRenderer();
        test.setEnvironment(true);
		
        test.init();test.doc();
		test.init();test.withSub();
	}
}