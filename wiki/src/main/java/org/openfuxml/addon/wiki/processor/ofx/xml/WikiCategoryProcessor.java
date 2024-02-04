package org.openfuxml.addon.wiki.processor.ofx.xml;

import java.io.File;

import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.addon.wiki.Category;
import org.openfuxml.model.xml.addon.wiki.Content;
import org.openfuxml.model.xml.addon.wiki.Page;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.ofx.Sections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiCategoryProcessor extends AbstractWikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiCategoryProcessor.class);
	
	private WikiPageProcessor pageProcessor;
	
	public WikiCategoryProcessor(WikiPageProcessor pageProcessor)
	{
		this.pageProcessor=pageProcessor;
	}
	
	public void processCategory(Content content) throws OfxAuthoringException, OfxInternalProcessingException
	{
		Category category = content.getCategory();
		Sections sections = new Sections();
		for(Page page : category.getPage())
		{
			page.setSection(new Section());
			logger.warn("HandlerType set manually !!!");
			Section section = new Section();
			section.setExternal(true);
			section.setSource(dstDir.getName()+"/"+page.getFile()+"."+WikiProcessor.WikiFileExtension.xml);
			sections.getContent().add(section);
			pageProcessor.processPage(page);
		}
		String fName = WikiContentIO.getFileFromSource(content.getSource())+"."+WikiProcessor.WikiFileExtension.xml;
		File f = new File(dstDir,fName);
		logger.debug("Writing categories external XML: "+f.getAbsolutePath());
		JaxbUtil.save(f, sections,true);
	}
}