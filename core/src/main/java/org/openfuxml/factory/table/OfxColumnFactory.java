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
}
