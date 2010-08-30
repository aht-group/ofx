package org.openfuxml.addon.wiki.util;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Locale;

import net.sf.exlp.io.LoggerInit;
import net.sourceforge.jwbf.actions.util.ActionException;
import net.sourceforge.jwbf.actions.util.ProcessException;
import net.sourceforge.jwbf.bots.MediaWikiBot;
import net.sourceforge.jwbf.contentRep.SimpleArticle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiTemplates;

public class WikiTextFetcher
{
	static Log logger = LogFactory.getLog(WikiTextFetcher.class);
	
	private String wikiText;
	
	public WikiTextFetcher()
	{
		
	}
	
	public String fetchText(String article)
	{
		try
		{
			MediaWikiBot bot = new MediaWikiBot("http://de.wikipedia.org/w/");
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