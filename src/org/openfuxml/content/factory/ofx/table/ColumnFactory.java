package org.openfuxml.content.factory.ofx.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.layout.Alignment;
import org.openfuxml.content.ofx.layout.Width;
import org.openfuxml.content.ofx.table.Column;

public class ColumnFactory
{
	static Log logger = LogFactory.getLog(ColumnFactory.class);
	
	public static synchronized Column create(Alignment alignment) 
	{
		Column column = new Column();
		
		column.setAlignment(alignment.toString().toLowerCase());
		
		return column;
	}
	
	public static synchronized Column create(Alignment alignment, double length) 
	{
		Width width = new Width();
		width.setValue(length);
		width.setUnit("*");
		
		Column column = new Column();
		column.setAlignment(alignment.toString().toLowerCase());
		column.setWidth(width);
		
		return column;
	}
}