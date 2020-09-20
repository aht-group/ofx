package org.openfuxml.renderer.wiki.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.doc.provider.old.ReferenceProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.wiki.AbstractTestWikiRenderer;
import org.openfuxml.renderer.wiki.WikiParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Testing the correct transformation of the xml file to wiki syntax
 * @author yannkruger
 *
 */
public class TestWikiReferenceRenderer extends AbstractTestWikiRenderer{
	
	final static Logger logger = LoggerFactory.getLogger(TestWikiReferenceRenderer.class);

	private enum Key {reference}
	
	private WikiParagraphRenderer renderer;

	//TODO FIX
	@Before public void init()
	{
		super.initDir("structure/reference");
//		renderer = new WikiParagraphRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager());
	}

	//TODO FIX
	@Ignore
	@Test public void reference() throws IOException, OfxAuthoringException
	{
		initFile(Key.reference);
        renderer.render(ReferenceProvider.create());
    	renderTest(renderer);
	}
	
	public static void main(String[] args) throws IOException, OfxAuthoringException
	{
		OfxCoreBootstrap.init();
		TestWikiReferenceRenderer test = new TestWikiReferenceRenderer();
        test.setEnvironment(true);
		
        test.init();test.reference();
	}
}
