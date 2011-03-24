package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;

public abstract class AbstractWikiProcessor
{
	static Log logger = LogFactory.getLog(AbstractWikiProcessor.class);
	
	protected File srcDir,dstDir;
	
	public void setDirectories(File srcDir, File dstDir)
	{
		this.srcDir=srcDir;
		this.dstDir=dstDir;
		logger.debug("Directory Src: "+srcDir.getAbsolutePath());
		logger.debug("Directory Dst: "+dstDir.getAbsolutePath());
	}
	
	public void process(Contents wikiQueries) throws OfxWikiException
	{
		for(Content content : wikiQueries.getContent())
		{
			if(content.isSetPage()){processPage(content.getPage());}
			else if(content.isSetCategory()){processCategory(content.getCategory());}
			else {throw new OfxWikiException("No "+WikiMarkupProcessor.class.getSimpleName()+" available for this element");}
		}
	}
	
	protected void processPage(Page page){logger.warn("Must be Overridden!");}
	protected void processCategory(Category category){logger.warn("Must be Overridden!");}
}
