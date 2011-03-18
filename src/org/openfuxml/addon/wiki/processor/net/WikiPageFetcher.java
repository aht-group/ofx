package org.openfuxml.addon.wiki.processor.net;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
import org.openfuxml.renderer.latex.util.TxtWriter;

public class WikiPageFetcher
{
	static Log logger = LogFactory.getLog(WikiPageFetcher.class);
	
	private MediaWikiBot bot;
	private String wikiText;

	private DecimalFormat df;
	
	public WikiPageFetcher(MediaWikiBot bot)
	{
		this.bot=bot;
		df = (DecimalFormat)DecimalFormat.getInstance(Locale.GERMAN);
		df.applyPattern( "#,###,##0" );
	}
	
	public void fetchText(String article)
	{
		try
		{	
			SimpleArticle sa = new SimpleArticle(bot.readContent(article));
				
		    wikiText = sa.getText();
		}
		catch (ActionException e) {logger.error(e);}
		catch (ProcessException e) {logger.error(e);}
		
		logger.debug("Article "+article+" fetched. "+df.format(wikiText.length())+" characters.");
	}
	
	public void save(TxtWriter txtWriter)
	{
		List<String> txt = new ArrayList<String>();
		txt.add(wikiText);
		txtWriter.writeFile(txt);
	}
	
	public String getWikiText() {return wikiText;}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WikiTemplates.init();	
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiPageFetcher wtf = new WikiPageFetcher(wbf.createBot());
		wtf.fetchText("Bellagio");
    }
}