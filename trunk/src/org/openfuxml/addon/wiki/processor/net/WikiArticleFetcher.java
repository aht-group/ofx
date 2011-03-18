package org.openfuxml.addon.wiki.processor.net;

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
import org.openfuxml.addon.wiki.util.WikiBotFactory;

public class WikiArticleFetcher
{
	static Log logger = LogFactory.getLog(WikiArticleFetcher.class);
	
	private MediaWikiBot bot;
	private String wikiText; 
	
	public WikiArticleFetcher(MediaWikiBot bot)
	{
		this.bot=bot;
	}
	
	public String fetchText(String article)
	{
		try
		{	
			SimpleArticle sa = new SimpleArticle(bot.readContent(article));
				
		    wikiText = sa.getText();
		}
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
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiArticleFetcher wtf = new WikiArticleFetcher(wbf.createBot());
		wtf.fetchText("Bellagio");
    }
}