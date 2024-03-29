package org.openfuxml.factory.xml.table;

import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory.Horizontal;
import org.openfuxml.model.xml.core.layout.Width;
import org.openfuxml.model.xml.core.table.Column;
import org.openfuxml.model.xml.core.table.Columns;

public class XmlColumnFactory
{
	public static void add(Columns cols, Horizontal hAlignment){cols.getColumn().add(XmlColumnFactory.build(hAlignment));}
	public static void flex(Columns cols, double value){cols.getColumn().add(XmlColumnFactory.flex(value));}
	
	public static Column build(Horizontal hAlignment)
	{
		Column col = new Column();
		col.setAlignment(XmlAlignmentFactory.buildHorizontal(hAlignment));
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
	
	public static Column relative(int relative)
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
	
	public static Column flex(double value){return flex(value,false);}
	public static Column flex(double value,boolean narrow)
	{
		Width width = new Width();
		width.setNarrow(narrow);
		width.setFlex(true);
		width.setValue(value);
		Column col = new Column();
		col.setWidth(width);
		return col;
	}
}
