package org.openfuxml.addon.wiki.processor.template;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;

public class WikiTemplateProcessor extends AbstractWikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateProcessor.class);
	
	public WikiTemplateProcessor() 
	{
		
	}
	
	public void process(Templates templates) throws OfxInternalProcessingException
	{
		logger.debug(this.getDir(WikiProcessor.WikiDir.wikiTemplate));
	}
	
}