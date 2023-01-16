package org.openfuxml.factory.xml.table;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Content;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlTableFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlTableFactory.class);
	
	private Table table; public Table getTable() {return table;} public void setTable(Table table) {this.table = table;}
	
	public static XmlTableFactory instance(String title) {return new XmlTableFactory(title);}
	private XmlTableFactory(String title)
	{
		table = build();
		table.setId(UUID.randomUUID().toString());
		table.setTitle(XmlTitleFactory.build(title));
		
		Content content = new Content();
		content.getBody().add(new Body());
		table.setContent(content);
	}
	
	public void addHeader(String... header)
	{
		Row row = new Row();
		for(String column : header)
		{
			row.getCell().add(XmlCellFactory.createParagraphCell(column));
		}
		table.getContent().setHead(XmlHeadFactory.build(row));
	}
	public void addRow(Object... cell) {this.addIdRow(null, cell);}
	public void addIdRow(String id, Object... cell)
	{
		Row row = new Row();
		row.setId(id);
		
		for(Object o : cell)
		{
			if(o!=null) {row.getCell().add(XmlCellFactory.createParagraphCell(o.toString()));}
			else {row.getCell().add(XmlCellFactory.createParagraphCell(""));}
		}
		table.getContent().getBody().get(0).getRow().add(row);
	}
	public boolean hasRowId(String id)
	{
		for(Body body : table.getContent().getBody())
		{
			for(Row row : body.getRow())
			{
				if(row.getId().equals(id)) {return true;}
			}
		}
		return false;
	}
	
	public static Table build() {return new Table();}
	
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
			for(int i=0;i<meta.getColumnCount();i++)
			{
				row[i] = rs.getObject(i+1);
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
	
	public static Table buildStringList(List<String> header, List<List<String>> data)
	{
		List<Object[]> list = new ArrayList<>();
		for(List<String> d : data)
		{
			Object[] itemsArray = new String[d.size()];
	        itemsArray = d.toArray(itemsArray);
			list.add(itemsArray);
		}
		return build(header,list);
	}
	
	public static Table build(List<String> header, List<Object[]> data){return build(null,header,data);}
	public static Table build2(List<String> header, List<List<Object>> data)
	{
		List<Object[]> list = new ArrayList<>();
		for(List<Object> l : data)
		{
			Object[] myArray = new Object[l.size()];
		    l.toArray(myArray);
		    list.add(myArray);
		}

		return build(null,header,list);
	}
	public static Table build(String title, List<String> header, List<Object[]> data)
	{
		Table table = new Table();
		if(title!=null){table.setTitle(XmlTitleFactory.build(title));}
		
		Content content = new Content();
		content.setHead(buildHead(header));
		content.getBody().add(buildBody(data));
		table.setContent(content);
		return table;
	}
	
	public static Table build(List<Object[]> data)
	{
		Table table = new Table();
		
		Content content = new Content();
		content.getBody().add(buildBody(data));
		table.setContent(content);
		return table;
	}
	
	private static Head buildHead(List<String> header)
	{
		Row row = new Row();
		for(String column : header)
		{
			row.getCell().add(XmlCellFactory.createParagraphCell(column));
		}
		return XmlHeadFactory.build(row);
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
				row.getCell().add(XmlCellFactory.createParagraphCell(o.toString()));
			}
			else
			{
				row.getCell().add(XmlCellFactory.createParagraphCell(""));
			}
		}
		return row;
	}
}