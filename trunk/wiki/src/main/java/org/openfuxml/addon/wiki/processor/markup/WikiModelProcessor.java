package org.openfuxml.addon.wiki.processor.markup;

import info.bliki.wiki.model.WikiModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;

public class WikiModelProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiModelProcessor.class);
	
	public WikiModelProcessor()
	{

	}
	
	@Override
	protected void processCategory(Content content)
	{
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	@Override
	protected void processPage(Content content)
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	private void processPage(Page page)
	{
		String fNameMarkup = page.getFile()+"."+WikiProcessor.WikiFileExtension.txt;
		String fNameModel = page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
		String txtMarkup = WikiContentIO.loadTxt(srcDir, fNameMarkup);
		String result = process(txtMarkup);
		WikiContentIO.writeTxt(dstDir, fNameModel, result);
	}
	
	public String process(String txtMarkup)
	{
		logger.warn("Check image and title");
		String wikiImage="file:///c:/temp/${image}";
		String wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(txtMarkup);
        return xHtml;
	}
}