package org.openfuxml.xml.xpath.wiki;

import net.sf.exlp.util.exception.ExlpXpathNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Auth;

public class WikiAuthXpath
{
	static Log logger = LogFactory.getLog(WikiAuthXpath.class);
	
	public static synchronized Auth getAuth(String serverId, String authType) throws ExlpXpathNotFoundException
	{
		throw new ExlpXpathNotFoundException("No "+Auth.class.getSimpleName()+" for server=serverId and authType="+authType);
	}
}