package org.openfuxml.factory.xml.table;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;

public class OfxCellFactory
{
	public static Cell build(){return new Cell();}
	
	public static Cell createParagraphCell(DecimalFormat df, double value){return createParagraphCell(df.format(value));}
	public static Cell createParagraphCell(int text){return createParagraphCell(""+text);}
	public static Cell createParagraphCell(double value){return createParagraphCell(""+value);}
	public static Cell createParagraphCell(long value){return createParagraphCell(""+value);}
	public static Cell createParagraphCell(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Cell cell = new Cell();
		cell.getContent().add(p);
		return cell;
	}
	
	public static Cell buildParagraph(String localeCode, String text)
	{
		Paragraph p = new Paragraph();
		p.setLang(localeCode);
		p.getContent().add(text);
		
		Cell cell = new Cell();
		cell.getContent().add(p);
		return cell;
	}
	
	// BUILD Allows null values
	public static Cell paragraph(String text)
	{
		if(text==null){text = "";}
		return createParagraphCell(text);
	}
	
	public static Cell image(Image image)
	{
		Cell cell = new Cell();
		cell.getContent().add(image);
		return cell;
	}
	
	public static List<Paragraph> toParagraphs(Cell cell)
	{
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		for(Serializable s : cell.getContent())
		{
			if(s instanceof Paragraph)
			{
				paragraphs.add((Paragraph)s);
			}
		}
		return paragraphs;
	}
}
