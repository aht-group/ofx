package org.openfuxml.factory.table;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Cell;

public class OfxCellFactory
{
	public static Cell createParagraphCell(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Cell cell = new Cell();
		cell.getContent().add(p);
		return cell;
	}
}
