package org.openfuxml.factory.xml.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	public static Table build(ResultSet rs) throws SQLException
	{
		ResultSetMetaData meta = rs.getMetaData();
		List<String> header = new ArrayList<String>();
		for(int i=1;i<=meta.getColumnCount();i++)
		{
			header.add(meta.getColumnLabel(i));
		}

		List<Object[]> data = new ArrayList<Object[]>();
		while(rs.next())
		{	
			Object[] row = new Object[meta.getColumnCount()];
			for(int i=1;i<meta.getColumnCount();i++)
			{
				row[i-1] = rs.getObject(i);
			}
			data.add(row);
		}
		
		return build(header,data);
	}
	
	public static Table build(String[] header, Object[][] data)
	{
		List<String> listHeader = new ArrayList<String>(Arrays.asList(header));
		List<Object[]> listData = new ArrayList<Object[]>(Arrays.asList(data));
		return build(listHeader,listData);
	}
	
	public static Table build(List<String> header, List<Object[]> data)
	{
		Table table = new Table();
		
		Content content = new Content();
		content.setHead(buildHead(header));
		content.getBody().add(buildBody(data));
		table.setContent(content);
		return table;
	}
	
	private static Head buildHead(List<String> header)
	{
		Row row = new Row();
		for(String column : header)
		{
			row.getCell().add(OfxCellFactory.createParagraphCell(column));
		}
		
		Head head = new Head();
		head.getRow().add(row);
		return head;
	}
	
	private static Body buildBody(List<Object[]> data)
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
			if(o!=null)
			{
				row.getCell().add(OfxCellFactory.createParagraphCell(o.toString()));
			}
			else
			{
				row.getCell().add(OfxCellFactory.createParagraphCell(""));
			}
		}
		
		return row;
	}
}
