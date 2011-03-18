package org.openfuxml.addon.wiki.processor.net;

import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Section;
import org.openfuxml.addon.wiki.util.WikiBotFactory;

public class WikiContentFetcher
{
	static Log logger = LogFactory.getLog(WikiContentFetcher.class);
	
	private WikiBotFactory wbf;
	
	public WikiContentFetcher(WikiBotFactory wbf)
	{
		this.wbf=wbf;
	}

	public void fetch(List<Content> lContent)
	{
		logger.debug("Fetching "+lContent.size()+" content elements");
		
		for(Content content : lContent)
		{
			if(content.isSetSection()){fetchPage(content.getSection());}
		}
	}
	
	private void fetchPage(Section section)
	{
		
	}
}
