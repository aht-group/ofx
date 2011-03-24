package org.openfuxml.addon.wiki.processor.ofx;

import java.io.File;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiCategoryProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class WikiXmlProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiXmlProcessor.class);
	
	private OfxNsPrefixMapper nsPrefixMapper;
	
	private WikiPageProcessor pageProcessor;
	private WikiCategoryProcessor categoryProcessor;
	
	public WikiXmlProcessor()
	{
		WikiTemplates.init();
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
	
	@Override
	protected void processCategory(Content content) throws OfxAuthoringException
	{
		getCategoryProcessor().processCategory(content);
	}
	
	@Override
	protected void processPage(Content content) throws OfxAuthoringException
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