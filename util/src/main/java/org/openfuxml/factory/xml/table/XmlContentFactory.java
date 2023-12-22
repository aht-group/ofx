package org.openfuxml.factory.xml.table;

import org.openfuxml.model.xml.core.table.Body;
import org.openfuxml.model.xml.core.table.Content;
import org.openfuxml.model.xml.core.table.Head;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlContentFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlContentFactory.class);
	
	public static Content build(Head head, Body body)
	{
		Content content = new Content();
		content.setHead(head);
		content.getBody().add(body);
		return content;
	}
	
	public static Content build(Body body)
	{
		Content content = new Content();
		content.getBody().add(body);
		return content;
	}
}