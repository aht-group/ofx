package org.openfuxml.test.addon.wiki;

import java.io.FileNotFoundException;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.addon.wiki.processor.markup.TestWikiModelProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.TestWikiPageProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.TestXhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.TestXhtmlReplaceProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Parameterized.class)
public class TstWikiTransformationChain
{
	final static Logger logger = LoggerFactory.getLogger(TstWikiTransformationChain.class);
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		OfxBootstrap.init();
		
		int id = 6;
		boolean saveReference=true;
			
//		TestWikiMarkupProcessor.chain();
		TestWikiModelProcessor.chain(id,saveReference);
		TestXhtmlReplaceProcessor.chain(id,saveReference);
		TestXhtmlFinalProcessor.chain(id,saveReference);
		TestWikiPageProcessor.chain(id,saveReference);
    }
}