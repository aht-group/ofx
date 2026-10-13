package org.openfuxml.addon.wiki.processor.net;

import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processor.markup.TestWikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiPageFetcher;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.test.AbstractFileProcessingTest;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliWikiPageFetcher extends AbstractFileProcessingTest
{
	final static Logger logger = LoggerFactory.getLogger(TestWikiInlineProcessor.class);
	
	public static void main(String[] args)
    {
		OfxBootstrap.init();
		
		WikiTemplates.init();	
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiPageFetcher wtf = new WikiPageFetcher(wbf.createBot());
		wtf.fetchText("Bellagio");
    }
}