package org.openfuxml.renderer.wiki.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openfuxml.OfxCoreBootstrap;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.doc.provider.text.SectionProvider;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.wiki.AbstractTestWikiRenderer;
import org.openfuxml.renderer.wiki.WikiSectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestWikiSectionRenderer extends AbstractTestWikiRenderer
{

	final static Logger logger = LoggerFactory.getLogger(TestWikiSectionRenderer.class);
	
	private enum Key {section}
	
	private Section section;
    private WikiSectionRenderer renderer;
    
    //TODO FIX
	@Before
	public void init()
	{
		super.initDir("structure/section");
//		renderer = new WikiSectionRenderer(new NoOpCrossMediaManager(), new OfxDefaultSettingsManager(),1);
		//TODO XML Dokument bauen
		//s. Section Provider (SVN UPDATE)
	}
	
	//TODO Fix
	@Ignore
	@Test 
	public void section() throws OfxAuthoringException, OfxConfigurationException, IOException
    {
		initFile(Key.section);
		renderer.render(SectionProvider.buildWithMultiLevels());
    	renderTest(renderer);
	}
	
	public static void main(String[] args) throws IOException, OfxAuthoringException, OfxConfigurationException
	{
		OfxCoreBootstrap.init();
		TestWikiSectionRenderer test = new TestWikiSectionRenderer();
        test.setEnvironment(true);
		
        test.init();test.section();
	}	
}