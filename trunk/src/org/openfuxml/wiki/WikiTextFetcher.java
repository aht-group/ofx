package org.openfuxml.wiki;

import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Locale;

import net.sourceforge.jwbf.actions.mw.util.ActionException;
import net.sourceforge.jwbf.actions.mw.util.ProcessException;
import net.sourceforge.jwbf.bots.MediaWikiBot;
import net.sourceforge.jwbf.contentRep.mw.SimpleArticle;

import org.apache.log4j.Logger;

import de.kisner.util.LoggerInit;

public class WikiTextFetcher
{
	private static Logger logger = Logger.getLogger(WikiTextFetcher.class);
	
	private String wikiText;
	
	public WikiTextFetcher()
	{
		
	}
	
	public String fetchText(String article)
	{
		try
		{
			MediaWikiBot b = new MediaWikiBot("http://de.wikipedia.org/w/");
			SimpleArticle sa = new SimpleArticle(b.readContent(article));
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