package org.openfuxml.renderer.word.structure;

import java.awt.Color;
import java.io.Serializable;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlFloatFactory;
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
	
	public enum tableAddBorderTo{first,mid,last,only};
	
	Document doc;
	DocumentBuilder builder;
	Table table;

	public WordTableRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void render(org.openfuxml.content.table.Table ofxTable,	int tableCount, int tableCurrent) throws OfxAuthoringException
	{		
		if(!ofxTable.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!ofxTable.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		if(ofxTable.getContent().getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		
		if(!ofxTable.getSpecification().isSetLong()){ofxTable.getSpecification().setLong(false);}
		if(!ofxTable.getSpecification().isSetFloat()){ofxTable.getSpecification().setFloat(XmlFloatFactory.build(false));}
		
		SetFont sF = new SetFont(doc, builder);
		
		table = builder.startTable();
		builder.insertCell();
		
		try	{table.setStyleOptions(TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);}catch(Exception e){}
				
		builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 / (ofxTable.getContent().getHead().getRow().get(0).getCell().size())));
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
	
		//builder.endTable();
		try
		{ 
			logger.debug("borders please!!!!"+tableCount+tableCurrent);
			if (tableCount==1) {tableAddGridAndBoarders(tableAddBorderTo.only);logger.debug("only");}
			
			else if (tableCount==2)
			{
				logger.debug("tablecount = 2 " );
				if (tableCurrent==1) {tableAddGridAndBoarders(tableAddBorderTo.first);logger.debug("2 tables - first");}
				if (tableCurrent==2) {tableAddGridAndBoarders(tableAddBorderTo.last);logger.debug("2 tables - last");}
			}
	
			else if ((tableCount>=3)&&(tableCurrent==1)) {tableAddGridAndBoarders(tableAddBorderTo.first);logger.debug(">3 tables - first");}
			else if ((tableCount>=3)&&(tableCurrent!=1)&&(tableCount!=tableCurrent)) {tableAddGridAndBoarders(tableAddBorderTo.mid);logger.debug(">3 tables - mid");}
			else if ((tableCount>=3)&&(tableCount==tableCurrent)) {tableAddGridAndBoarders(tableAddBorderTo.last);logger.debug(">3 tables - last");}
		}
		catch (Exception e)
		{}
		builder.endTable();
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
						case mid:
							cell.getCellFormat().getBorders().getTop().setLineStyle(LineStyle.NONE);
							cell.getCellFormat().getBorders().getTop().setLineWidth(0.0);
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
						case mid:
							cell.getCellFormat().getBorders().getBottom().setLineStyle(LineStyle.NONE);
							cell.getCellFormat().getBorders().getBottom().setLineWidth(0.0);
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
