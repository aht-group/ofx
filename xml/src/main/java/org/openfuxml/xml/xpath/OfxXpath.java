package org.openfuxml.xml.xpath;

import org.jdom2.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxXpath
{
	final static Logger logger = LoggerFactory.getLogger(OfxXpath.class);
	
	public static synchronized Namespace getNsHtml()
	{
		Namespace nsHtml = Namespace.getNamespace("html", "http://www.openfuxml.org/renderer/html");
		return nsHtml;
	}
	
//	public static NsPrefixMapperInterface getNsPrefixMapper()
//	{
//		return new OfxNsPrefixMapper();
//	}
}