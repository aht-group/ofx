package org.openfuxml.util.query;

import java.io.Serializable;
import java.util.List;

import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.ofx.Paragraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlContentQuery
{
	final static Logger logger = LoggerFactory.getLogger(XmlContentQuery.class);
	
	public static boolean hasMore(Object o, List<Serializable> list)
	{
		int index = list.indexOf(o)+1;
		boolean hasMore = false;
		for(Object x : list.subList(index, list.size()-1))
		{
			if(x instanceof Paragraph) {hasMore=true;}
			if(x instanceof Item) {hasMore=true;}
		}
		return hasMore;
	}
	
	
}