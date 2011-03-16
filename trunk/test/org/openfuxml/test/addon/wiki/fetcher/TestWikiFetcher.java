package org.openfuxml.test.addon.wiki.fetcher;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;
import org.openfuxml.addon.wiki.util.WikiTextFetcher;

public class TestWikiFetcher
{
	static Log logger = LogFactory.getLog(TestWikiFetcher.class);
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		WikiConfigChecker.check(config);
			
		WikiTextFetcher tw = new WikiTextFetcher();
//		String wikiText = tw.fetchText(article);
//		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtFetched+".txt", wikiText);
    }
}