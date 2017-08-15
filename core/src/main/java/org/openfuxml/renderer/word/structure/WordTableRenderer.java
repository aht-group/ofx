package org.openfuxml.renderer.word.structure;

import java.awt.Color;
import java.io.Serializable;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.LineStyle;
import com.aspose.words.PreferredWidth;
import com.aspose.words.Row;
import com.aspose.words.Table;
import com.aspose.words.TableStyleOptions;

public class WordTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordParagraphRenderer.class);
	
	private enum tableAddBorderTo{first,last,only};
	
	Document doc;
	DocumentBuilder builder;
	Table table;

	public WordTableRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void render(org.openfuxml.content.table.Table ofxTable)
	{
		SetFont sF = new SetFont(doc, builder);
		table = builder.startTable();
		builder.insertCell();
		try
		{
			table.setStyleOptions(
					TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);
		}
		catch (Exception e)
		{}

		builder.getCellFormat().setPreferredWidth(
				PreferredWidth.fromPercent(100 / (ofxTable.getContent().getHead().getRow().size() + 1)));
		builder.getParagraphFormat().setSpaceBeforeAuto(false);
		builder.getParagraphFormat().setSpaceBefore(4);
		builder.getParagraphFormat().setSpaceAfter(4);

		// header
		int columnHelper = 1;
		for (org.openfuxml.content.table.Row row : ofxTable.getContent().getHead().getRow())
		{
			sF.setFont(setFontEnum.textheader);
			for (org.openfuxml.content.table.Cell cell : row.getCell())
			{
				for (Serializable s : cell.getContent())
				{
					if (s instanceof org.openfuxml.content.ofx.Paragraph)
					{
						logger.trace("WordSectionRenderer.paragraphRenderer()");
						WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
						wPF.render((org.openfuxml.content.ofx.Paragraph) s);
					}
				}
				if ((columnHelper) <= (row.getCell().size() - 1))
				{
					builder.insertCell();
				}
				columnHelper++;
			}
		}

		builder.endRow();

		// body

		for (org.openfuxml.content.table.Body b : ofxTable.getContent().getBody())
		{

			builder.insertCell();
			sF.setFont(setFontEnum.text);
			int rowHelper = 1;
			for (org.openfuxml.content.table.Row row : b.getRow())
			{
				int columnHelper2 = 1;
				for (org.openfuxml.content.table.Cell cell : row.getCell())
				{
					for (Serializable s : cell.getContent())
					{
						if (s instanceof org.openfuxml.content.ofx.Paragraph)
						{
							logger.trace("WordSectionRenderer.paragraphRenderer()");
							WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
							wPF.render((org.openfuxml.content.ofx.Paragraph) s);
						}
					}
					if ((columnHelper2) <= (row.getCell().size() - 1))
					{
						builder.insertCell();
					}
					columnHelper2++;

				}
				builder.endRow();
				if (rowHelper <= b.getRow().size() - 1)
				{
					builder.insertCell();
				}
				rowHelper++;
			}

		}
	
		builder.endTable();
		try
		{
			tableAddGridAndBoarders(tableAddBorderTo.only);
		}
		catch (Exception e)
		{}

	}

	private void tableAddGridAndBoarders(tableAddBorderTo tABO) throws Exception
	{
		table.setBorders(LineStyle.SINGLE, 0.0, Color.BLACK);
		for (Row row : table.getRows())
		{
			for (Cell cell : row.getCells())
			{
				cell.getCellFormat().getBorders().setLineStyle(LineStyle.SINGLE);
				cell.getCellFormat().getBorders().setLineWidth(0.5);
			}
		}
		for (Row row : table.getRows())
		{
			for (Cell cell : row.getCells())
			{
				if (row == table.getFirstRow())
				{
					switch (tABO)
					{
						case first:
							cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.DOUBLE);
							cell.getCellFormat().getBorders().getTop().setLineWidth(0.5);
							break;
						case last:
							cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.SINGLE);
							cell.getCellFormat().getBorders().getTop().setLineWidth(0.5);
							break;
						case only:
							cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.DOUBLE);
							cell.getCellFormat().getBorders().getTop().setLineWidth(0.5);
							break;
					}
				}
				else if (row == table.getLastRow())
				{
					switch (tABO)
					{
						case first:
							cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.SINGLE);
							cell.getCellFormat().getBorders().getBottom().setLineWidth(0.5);
							break;
						case last:
							cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.DOUBLE);
							cell.getCellFormat().getBorders().getBottom().setLineWidth(0.5);
							break;
						case only:
							cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.DOUBLE);
							cell.getCellFormat().getBorders().getBottom().setLineWidth(0.5);
							break;
					}
				}
				if (cell == row.getFirstCell())
				{
					cell.getCellFormat().getBorders().getLeft().setLineStyle(LineStyle.DOUBLE);
					cell.getCellFormat().getBorders().getLeft().setLineWidth(0.5);
				}
				if (cell == row.getLastCell())
				{
					cell.getCellFormat().getBorders().getRight().setLineStyle(LineStyle.DOUBLE);
					cell.getCellFormat().getBorders().getRight().setLineWidth(0.5);
				}
			}
		}
	}
}
