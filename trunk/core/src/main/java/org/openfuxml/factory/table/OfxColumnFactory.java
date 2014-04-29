package org.openfuxml.factory.table;

import org.openfuxml.content.layout.Width;
import org.openfuxml.content.table.Column;

public class OfxColumnFactory
{
	public static Column createCol(int relative)
	{
		Width width = new Width();
		width.setValue(relative);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column flex()
	{
		Width width = new Width();
		width.setFlex(true);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column flex(double value)
	{
		Width width = new Width();
		width.setFlex(true);
		width.setValue(value);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
	
	public static Column percentage(double value)
	{
		Width width = new Width();
		width.setUnit("percentage");
		width.setValue(value);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
}
