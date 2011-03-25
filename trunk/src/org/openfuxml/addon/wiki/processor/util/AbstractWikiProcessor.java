package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;

public abstract class AbstractWikiProcessor
{
	static Log logger = LogFactory.getLog(AbstractWikiProcessor.class);
	
	protected File srcDir,dstDir;
	
	public void setDirectories(File srcDir, File dstDir)
	{
		this.srcDir=srcDir;
		this.dstDir=dstDir;
		if(srcDir!=null){logger.debug("Directory Src: "+srcDir.getAbsolutePath());}
		if(dstDir!=null){logger.debug("Directory Dst: "+dstDir.getAbsolutePath());}
	}
	
	public void process(Contents wikiQueries) throws OfxWikiException, OfxAuthoringException, OfxInternalProcessingException
	{
		for(Content content : wikiQueries.getContent())
		{
			if(content.isSetPage()){processPage(content);}
			else if(content.isSetCategory()){processCategory(content);}
			else {throw new OfxWikiException("No "+WikiMarkupProcessor.class.getSimpleName()+" available for this element");}
		}
	}
	
	protected void processPage(Content content) throws OfxAuthoringException, OfxInternalProcessingException {logger.warn("Must be Overridden!");}
	protected void processCategory(Content content) throws OfxAuthoringException, OfxInternalProcessingException {logger.warn("Must be Overridden!");}
}
