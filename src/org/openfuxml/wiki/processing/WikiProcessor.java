package org.openfuxml.wiki.processing;

import org.apache.log4j.Logger;

public class WikiProcessor
{
	static Logger logger = Logger.getLogger(WikiProcessor.class);
	
	public WikiProcessor()
	{
		
	}
	
	public String process(String wikiText)
	{
		wikiText = wikiText.replaceAll("<noinclude>", "");
		wikiText = wikiText.replaceAll("</noinclude>", "");
		return wikiText;
	}

}
