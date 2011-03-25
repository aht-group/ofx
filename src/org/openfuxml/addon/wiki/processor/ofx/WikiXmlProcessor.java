package org.openfuxml.addon.wiki.processor.ofx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiCategoryProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;

public class WikiXmlProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiXmlProcessor.class);
	
	private WikiPageProcessor pageProcessor;
	private WikiCategoryProcessor categoryProcessor;
	
	public WikiXmlProcessor()
	{
		WikiTemplates.init();
	}
	
	@Override
	protected void processCategory(Content content) throws OfxAuthoringException, OfxInternalProcessingException
	{
		getCategoryProcessor().processCategory(content);
	}
	
	@Override
	protected void processPage(Content content) throws OfxAuthoringException, OfxInternalProcessingException
	{
		Page page = content.getPage();
		getPageProcessor().processPage(page);
	}
	
	private WikiPageProcessor getPageProcessor()
	{
		if(pageProcessor==null)
		{
			pageProcessor = new WikiPageProcessor();
			pageProcessor.setDirectories(srcDir, dstDir);
		}
		return pageProcessor;
	}
	
	private WikiCategoryProcessor getCategoryProcessor()
	{
		if(categoryProcessor==null)
		{
			categoryProcessor = new WikiCategoryProcessor(getPageProcessor());
			categoryProcessor.setDirectories(srcDir, dstDir);
		}
		return categoryProcessor;
	}
}