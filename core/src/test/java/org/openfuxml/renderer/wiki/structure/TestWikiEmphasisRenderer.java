package org.openfuxml.renderer.wiki.structure;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.text.EmphasisProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.wiki.AbstractTestWikiRenderer;
import org.openfuxml.renderer.wiki.WikiParagraphRenderer;
import org.openfuxml.util.configuration.settings.OfxDefaultSettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Testing the correct transformation of the xml file to wiki syntax
 * @author yannkruger
 *
 */
public class TestWikiEmphasisRenderer extends AbstractTestWikiRenderer{


	final static Logger logger = LoggerFactory.getLogger(TestWikiEmphasisRenderer.class);

	private enum Key {italic, bold, combination}
	
	private WikiParagraphRenderer renderer;

	@Before public void init()
	{
		super.initDir("structure/emphasis");
		renderer = new WikiParagraphRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	@Test public void italic() throws IOException, OfxAuthoringException
	{
		initFile(Key.italic);
        renderer.render(EmphasisProvider.italic());
    	renderTest(renderer);
	}
	
	@Test public void bold() throws IOException, OfxAuthoringException
	{
		initFile(Key.bold);
        renderer.render(EmphasisProvider.bold());
    	renderTest(renderer);
	}
	

	@Test public void combination() throws IOException, OfxAuthoringException
	{
		initFile(Key.combination);
		renderer.render(EmphasisProvider.italicBold());
		renderTest(renderer);
	}
	

	public static void main(String[] args) throws IOException, OfxAuthoringException
	{
		OfxCoreBootstrap.init();
		TestWikiEmphasisRenderer test = new TestWikiEmphasisRenderer();
        test.setEnvironment(true);
		
        test.init();test.bold();
        test.init();test.italic();
		test.init();test.combination();
	}
}