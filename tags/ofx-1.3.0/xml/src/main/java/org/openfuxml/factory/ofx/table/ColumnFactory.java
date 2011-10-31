package org.openfuxml.factory.ofx.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.layout.Alignment;
import org.openfuxml.content.ofx.layout.Width;
import org.openfuxml.content.ofx.table.Column;

public class ColumnFactory
{
	static Log logger = LogFactory.getLog(ColumnFactory.class);
	
	public static synchronized Column create(String alignmentHorizontal) 
	{
		Alignment alignment = new Alignment();
		alignment.setHorizontal(alignmentHorizontal);
		
		Column column = new Column();
		column.setAlignment(alignment);
		
		return column;
	}
	
	public static synchronized Column create(String alignmentHorizontal, double length) 
	{
		Width width = new Width();
		width.setValue(length);
		width.setUnit("*");
		
		Alignment alignment = new Alignment();
		alignment.setHorizontal(alignmentHorizontal);
		
		Column column = new Column();
		column.setAlignment(alignment);
		column.setWidth(width);
		
		return column;
	}
}