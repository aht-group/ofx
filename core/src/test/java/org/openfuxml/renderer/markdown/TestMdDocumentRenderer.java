package org.openfuxml.renderer.markdown;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.OfxConfigurationProvider;
import org.openfuxml.renderer.markdown.structure.MdDocumentRenderer;
import org.openfuxml.renderer.markdown.structure.MdParagraphRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.DocumentProvider;
import org.openfuxml.test.provider.EmphasisProvider;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestMdDocumentRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdDocumentRenderer.class);

	private enum Key {doc}
	
	private MdDocumentRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure");
		renderer = new MdDocumentRenderer(new OfxConfigurationProvider());
	}

	@Test public void test() throws IOException, OfxConfigurationException, OfxAuthoringException
	{
		initFile(Key.doc);
		renderer.render(DocumentProvider.build().getContent());
		renderTest(renderer);
	}

	public static void main(String[] args) throws Exception
	{
		OfxCoreTestBootstrap.init();
		TestMdDocumentRenderer test = new TestMdDocumentRenderer();
        test.setEnvironment(true);
		
		test.init();test.test();
	}
}