package org.openfuxml.renderer.wiki.structure;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.wiki.AbstractTestWikiRenderer;
import org.openfuxml.renderer.wiki.WikiSectionRenderer;
import org.openfuxml.test.OfxCoreTestBootstrap;
import org.openfuxml.test.provider.SectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWikiSectionRenderer extends AbstractTestWikiRenderer{

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
		
	@Test 
	public void section() throws OfxAuthoringException, OfxConfigurationException, IOException
    {
		initFile(Key.section);
		renderer.render(SectionProvider.buildWithMultiLevels());
    	renderTest(renderer);
	}
	
	public static void main(String[] args) throws IOException, OfxAuthoringException, OfxConfigurationException
	{
		OfxCoreTestBootstrap.init();
		TestWikiSectionRenderer test = new TestWikiSectionRenderer();
        test.setEnvironment(true);
		
        test.init();test.section();
	}	
}