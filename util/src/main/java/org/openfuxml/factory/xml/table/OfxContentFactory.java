package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxContentFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxContentFactory.class);
	
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