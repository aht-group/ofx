package org.openfuxml.addon.wiki.processor.net.fetcher;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.queries.CategoryMembersSimple;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.renderer.processor.latex.util.TxtWriter;

public class WikiCategoryFetcher
{
	static Log logger = LogFactory.getLog(WikiCategoryFetcher.class);
	
	private MediaWikiBot bot;
	private List<String> articleNames;
	private String targetFilePrefix;
	
	public WikiCategoryFetcher(MediaWikiBot bot)
	{
		this.bot=bot;
		articleNames = new ArrayList<String>();
		targetFilePrefix = "noPrefixDefinded";
	}
	
	public void fetchCategory(String catName)
	{
		logger.debug("Fetching all articles for "+catName);
		try
		{
			CategoryMembersSimple cms = new CategoryMembersSimple(bot,catName);
			Iterator<String> wikiArticles = cms.iterator();
			while(wikiArticles.hasNext())
			{
				articleNames.add(wikiArticles.next());
			}
		}
		catch (ActionException e) {logger.error(e);}
		catch (ProcessException e) {logger.error(e);}
	}
	
	public void fetchArticles(TxtWriter txtWriter, Category category)
	{
		WikiPageFetcher wpf = new WikiPageFetcher(bot);
		for(int i=0;i<articleNames.size();i++)
		{
			Page page = new Page();
			page.setName(articleNames.get(i));
			page.setFile(targetFilePrefix+i);
			txtWriter.setTargetFile(page.getFile()+"."+WikiProcessor.WikiFileExtension.txt);
			wpf.fetchText(page.getName());
			wpf.save(txtWriter);
			category.getPage().add(page);
		}
	}
	
	public void setTargetFilePrefix(String targetFilePrefix)
	{
		this.targetFilePrefix=targetFilePrefix;
	}

	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WikiTemplates.init();	
			
		WikiBotFactory wbf = new WikiBotFactory();
		WikiCategoryFetcher wtf = new WikiCategoryFetcher(wbf.createBot());
		wtf.fetchCategory("Laserphysik");
    }
}