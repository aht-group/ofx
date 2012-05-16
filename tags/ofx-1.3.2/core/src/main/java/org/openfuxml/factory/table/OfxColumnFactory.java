package org.openfuxml.factory.table;

import org.openfuxml.content.ofx.layout.Width;
import org.openfuxml.content.ofx.table.Column;

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
