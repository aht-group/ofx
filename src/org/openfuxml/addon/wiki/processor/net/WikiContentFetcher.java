package org.openfuxml.addon.wiki.processor.net;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiCategoryFetcher;
import org.openfuxml.addon.wiki.processor.net.fetcher.WikiPageFetcher;
import org.openfuxml.addon.wiki.processor.util.WikiBotFactory;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.renderer.latex.util.TxtWriter;

public class WikiContentFetcher
{
	static Log logger = LogFactory.getLog(WikiContentFetcher.class);
	
	private WikiBotFactory wbf;
	private TxtWriter txtWriter;
	private File dirXmlOfx;

	public WikiContentFetcher(WikiBotFactory wbf)
	{
		this.wbf=wbf;
		txtWriter = new TxtWriter();
		File currentDir = new File(".");
		setTargetDirs(currentDir,currentDir);
	}

	public void fetch(Contents wikiQueries) throws OfxWikiException
	{
		logger.debug("Fetching "+wikiQueries.getContent().size()+" content elements");
		
		for(Content content : wikiQueries.getContent())
		{
			if(content.isSetPage()){fetchPage(content);}
			else if(content.isSetCategory()){fetchCategory(content);}
			else {throw new OfxWikiException("No "+WikiContentFetcher.class.getSimpleName()+" available for this element");}
		}
	}
	
	private void fetchPage(Content content)
	{
		Page page = content.getPage();
		page.setWikiPlain(WikiContentIO.getFileFromSource(content.getSource(), "txt"));
		txtWriter.setTargetFile(page.getWikiPlain());
		WikiPageFetcher wpf = new WikiPageFetcher(wbf.getBot());
		wpf.fetchText(page.getName());
		wpf.save(txtWriter);
	}
	
	private void fetchCategory(Content content)
	{
		Category category = content.getCategory();
		WikiCategoryFetcher wcf = new WikiCategoryFetcher(wbf.getBot());
		wcf.fetchCategory(category.getName());
		wcf.setTargetFilePrefix(WikiContentIO.getFileFromSource(content.getSource(), ""));
		wcf.fetchArticles(txtWriter,category);
		wcf.createExternalXml(dirXmlOfx);
	}
	
	public void setTargetDirs(File dirWikiPlain, File dirXmlOfx)
	{
		txtWriter.setTargetDir(dirWikiPlain);
		this.dirXmlOfx=dirXmlOfx;
	}
}
