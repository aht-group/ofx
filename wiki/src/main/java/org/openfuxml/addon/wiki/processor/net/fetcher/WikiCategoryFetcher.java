package org.openfuxml.addon.wiki.processor.net.fetcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.exlp.util.io.txt.ExlpTxtWriter;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.model.xml.addon.wiki.Category;
import org.openfuxml.model.xml.addon.wiki.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.jwbf.core.actions.util.ActionException;
import net.sourceforge.jwbf.core.actions.util.ProcessException;
import net.sourceforge.jwbf.mediawiki.actions.queries.CategoryMembersSimple;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

public class WikiCategoryFetcher
{
	final static Logger logger = LoggerFactory.getLogger(WikiCategoryFetcher.class);
	
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
		catch (ActionException e) {logger.error("",e);}
		catch (ProcessException e) {logger.error("",e);}
	}
	
	public void fetchArticles(ExlpTxtWriter txtWriter, Category category)
	{
		WikiPageFetcher wpf = new WikiPageFetcher(bot);
		for(int i=0;i<articleNames.size();i++)
		{
			txtWriter.clear();
			Page page = new Page();
			page.setName(articleNames.get(i));
			page.setFile(targetFilePrefix+i);
			txtWriter.setFileName(page.getFile()+"."+WikiProcessor.WikiFileExtension.txt);
			wpf.fetchText(page.getName());
			wpf.save(txtWriter);
			category.getPage().add(page);
		}
	}
	
	public void setTargetFilePrefix(String targetFilePrefix)
	{
		this.targetFilePrefix=targetFilePrefix;
	}
}