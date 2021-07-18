package org.openfuxml.util.query;

import org.openfuxml.addon.wiki.data.jaxb.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpXpathNotFoundException;

public class WikiAuthXpath
{
	final static Logger logger = LoggerFactory.getLogger(WikiAuthXpath.class);
	
	public static synchronized Auth getAuth(String serverId, String authType) throws ExlpXpathNotFoundException
	{
		throw new ExlpXpathNotFoundException("No "+Auth.class.getSimpleName()+" for server=serverId and authType="+authType);
	}
}