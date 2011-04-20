package org.openfuxml.addon.wiki.processor.ofx.xml;

import java.io.File;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

public class WikiCategoryProcessor extends AbstractWikiProcessor
{
	static Log logger = LogFactory.getLog(WikiCategoryProcessor.class);
	
	private OfxNsPrefixMapper nsPrefixMapper;
	private WikiPageProcessor pageProcessor;
	
	public WikiCategoryProcessor(WikiPageProcessor pageProcessor)
	{
		this.pageProcessor=pageProcessor;
		nsPrefixMapper = new OfxNsPrefixMapper();
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
		JaxbUtil.save(f, sections, nsPrefixMapper,true);
	}
}