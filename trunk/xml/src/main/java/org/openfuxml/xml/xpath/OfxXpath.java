package org.openfuxml.xml.xpath;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Namespace;
import org.openfuxml.xml.ns.OfxNsPrefixMapper;

public class OfxXpath
{
	static Log logger = LogFactory.getLog(OfxXpath.class);
	
	public static synchronized Namespace getNsHtml()
	{
		Namespace nsHtml = Namespace.getNamespace("html", "http://www.openfuxml.org/renderer/html");
		return nsHtml;
	}
	
	public static synchronized NsPrefixMapperInterface getNsPrefixMapper()
	{
		return new OfxNsPrefixMapper();
	}
}