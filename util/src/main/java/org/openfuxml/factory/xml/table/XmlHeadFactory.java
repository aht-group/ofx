package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHeadFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlHeadFactory.class);
	
	public static Head build() {return new Head();}
	
	public static Head build(Row row)
	{
		Head xml = build();
		xml.getRow().add(row);
		return xml;
	}
	
	public static Head build(Cell... cells)
	{
		Row row = new Row();
		for(Cell c : cells)
		{
			row.getCell().add(c);
		}
		return build(row);
	}
}