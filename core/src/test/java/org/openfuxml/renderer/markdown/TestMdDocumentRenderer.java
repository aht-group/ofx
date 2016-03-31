package org.openfuxml.renderer.markdown;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.markdown.structure.MdDocumentRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.DocumentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestMdDocumentRenderer extends AbstractTestMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TestMdDocumentRenderer.class);

	private enum Key {doc}
	
	private MdDocumentRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure");
		renderer = new MdDocumentRenderer(cp);
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