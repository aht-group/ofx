package org.openfuxml.wiki.processing;

import org.apache.log4j.Logger;

public class XhtmlProcessor
{
	static Logger logger = Logger.getLogger(XhtmlProcessor.class);
	
	public XhtmlProcessor()
	{
		
	}
	
	public String process(String xHtml)
	{
		xHtml = xHtml.replace("&#160;", " ");
		xHtml = xHtml.replaceAll("&nbsp;", " ");
		return xHtml;
	}

}
