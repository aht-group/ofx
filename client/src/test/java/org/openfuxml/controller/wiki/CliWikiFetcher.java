package org.openfuxml.controller.wiki;

import org.apache.commons.configuration.Configuration;
import org.openfuxml.OfxClientBootstrap;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiPageFetcher;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliWikiFetcher
{
	final static Logger logger = LoggerFactory.getLogger(CliWikiFetcher.class);
	
	public static void main(String[] args)
    {
		Configuration config = OfxClientBootstrap.init();
				
		WikiBotFactory wbf = new WikiBotFactory();
		wbf.setUrl(config.getString("test.wiki.url"));
//		wbf.setHttpDigestAuth(config.getString("wiki.http.user"), config.getString("wiki.http.password"));
//		wbf.setWikiAuth(config.getString("wiki.user"), config.getString("wiki.password"));
		
		WikiPageFetcher tw = new WikiPageFetcher(wbf.createBot());
		tw.fetchText("Category:Use_Case");
		String wikiText = tw.getWikiText();
		logger.debug(wikiText);
//		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtFetched+".txt", wikiText);
    }
}