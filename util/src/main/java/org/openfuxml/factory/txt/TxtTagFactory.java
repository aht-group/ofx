package org.openfuxml.factory.txt;

import org.openfuxml.interfaces.xml.OfxEmphasis;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TxtTagFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtTagFactory.class);
	
	public static String tag(Class<?> c)
	{
		StringBuffer sb = new StringBuffer();
		
		if(c.isAssignableFrom(OfxEmphasis.class))
		{
			sb.append(OfxNsPrefixMapper.prefix(OfxNsPrefixMapper.NS.text));
			sb.append(":").append(OfxEmphasis.tag);
		}
		else
		{
			logger.error("YY");
		}
		
		return sb.toString();
	}
}
