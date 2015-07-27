package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxTableFactory.class);
	
	public static Table build(String[] columns, Object[][] data)
	{
		Table table = new Table();
		
		Content content = new Content();
		content.setHead(buildHead(columns));
		content.getBody().add(buildBody(data));
		table.setContent(content);
		return table;
	}
	
	private static Head buildHead(String[] columns)
	{
		Row row = new Row();
		for(String column : columns)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(column));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		return head;
	}
	
	private static Body buildBody(Object[][] data)
	{
		Body body = new Body();
		for(Object[] row : data)
		{
			body.getRow().add(buildRow(row));
		}
		return body;
	}
	
	private static Row buildRow(Object[] data)
	{		
		Row row = new Row();
		
		for(Object o : data)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(o.toString()));
		}
		
		return row;
	}
}
