package org.openfuxml.addon.wiki.util;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Locale;

import net.sf.exlp.io.LoggerInit;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.core.contentRep.SimpleArticle;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiTemplates;

public class WikiTextFetcher
{
	static Log logger = LogFactory.getLog(WikiTextFetcher.class);
	
	private String wikiURL;
	private String wikiText; 
	
	public WikiTextFetcher()
	{
		this("http://de.wikipedia.org/w/");
	}
	
	public WikiTextFetcher(String wikiURL)
	{
		this.wikiURL=wikiURL;
	}
	
	public String fetchText(String article)
	{
		try
		{
			MediaWikiBot bot = new MediaWikiBot(wikiURL);
			SimpleArticle sa = new SimpleArticle(bot.readContent(article));
		    wikiText = sa.getText();
		}
		catch (MalformedURLException e) {logger.error(e);}
		catch (ActionException e) {logger.error(e);}
		catch (ProcessException e) {logger.error(e);}
		
		DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance(Locale.GERMAN);
		df.applyPattern( "#,###,##0" );
		logger.debug("Article "+article+" fetched. "+df.format(wikiText.length())+" characters.");
		return wikiText;
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WikiTemplates.init();	
			
		WikiTextFetcher wtf = new WikiTextFetcher();
		wtf.fetchText("Bellagio");
    }
}