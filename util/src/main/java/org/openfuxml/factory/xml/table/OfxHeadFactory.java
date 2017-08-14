package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxHeadFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxHeadFactory.class);
	
	public static Head build(Row row)
	{
		Head xml = build();
		xml.getRow().add(row);
		return xml;
	}
	
	public static Head build()
	{
		Head xml = new Head();
		return xml;
	}
}