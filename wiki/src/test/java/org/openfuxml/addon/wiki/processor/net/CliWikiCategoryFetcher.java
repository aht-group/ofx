package org.openfuxml.addon.wiki.processor.net;

import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiCategoryFetcher;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliWikiCategoryFetcher extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		
		WikiTemplates.init();	
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiCategoryFetcher wtf = new WikiCategoryFetcher(wbf.createBot());
		wtf.fetchCategory("Laserphysik");
    }
}