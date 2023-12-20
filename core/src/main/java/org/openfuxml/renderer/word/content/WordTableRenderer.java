package org.openfuxml.renderer.word.content;

import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Cell;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.LineStyle;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.PreferredWidth;
import com.aspose.words.Row;
import com.aspose.words.Table;
import com.aspose.words.TableStyleOptions;

public class WordTableRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordTableRenderer.class);
	
	public enum tableAddBorderTo{first,mid,last,only};
	
	private Document doc;
	private DocumentBuilder builder;
	private Table table;

	public WordTableRenderer(Document doc, DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.content.table.Table ofxTable,	int tableCount, int tableCurrent) throws Exception
	{	
		// exeptions..
		//if(!ofxTable.isSetSpecification()){throw new OfxAuthoringException("<table> without <specification>");}
		if(!ofxTable.isSetContent()){throw new OfxAuthoringException("<table> without <content>");}
		if(ofxTable.getContent().getBody().size()!=1){throw new OfxAuthoringException("<content> must exactly have 1 body!");}
		//if(!ofxTable.getSpecification().isSetLong()){ofxTable.getSpecification().setLong(false);}
		//if(!ofxTable.getSpecification().isSetFloat()){ofxTable.getSpecification().setFloat(XmlFloatFactory.build(false));}

		SetFont sF = new SetFont(doc, builder);
		
		//start table and formating..
		table = builder.startTable();
		builder.insertCell();
		
		try	{table.setStyleOptions(TableStyleOptions.FIRST_COLUMN | TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);}catch(Exception e){}

		if(Objects.nonNull(ofxTable.getSpecification()))
		{
			builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(100 / (ofxTable.getContent().getHead().getRow().get(0).getCell().size())));
		}
		
		if (Objects.nonNull(ofxTable.getSpecification())) 
		{
			if (Objects.nonNull(ofxTable.getSpecification().getAlignment()))
			{
				if(Objects.nonNull(ofxTable.getSpecification().getAlignment().getHorizontal()))
				{
					builder.getParagraphFormat().setAlignment(ofxTable.getSpecification().getAlignment().getHorizontal().length());
				}
			}
	
			if (Objects.nonNull(ofxTable.getSpecification().getWidth()))
			{
				if (ofxTable.getSpecification().getWidth().isFlex()==true)
				{
					builder.getCellFormat().setPreferredWidth((PreferredWidth.AUTO));
				}
				else if (Objects.isNull(ofxTable.getSpecification().getWidth()))
				{
					if (Objects.nonNull(ofxTable.getSpecification().getWidth().getUnit()))
					{
						if (ofxTable.getSpecification().getWidth().getUnit()=="percentage")
						{
							builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(ofxTable.getSpecification().getWidth().getValue()));
						}
						else if(ofxTable.getSpecification().getWidth().getUnit()=="absolute")
						{
							builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPoints(ofxTable.getSpecification().getWidth().getValue()));
						}	
					}
				}
			}
		}
		
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
						WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
						wPF.render((org.openfuxml.content.ofx.Paragraph) s, tableCount, tableCurrent);
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
							WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
							wPF.render((org.openfuxml.content.ofx.Paragraph) s, tableCount, tableCurrent);
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
	
		if (tableCount==1) {tableAddGridAndBoarders(tableAddBorderTo.only);}
			
		else if (tableCount==2)
		{
			if (tableCurrent==1) {tableAddGridAndBoarders(tableAddBorderTo.first);}
			if (tableCurrent==2) {tableAddGridAndBoarders(tableAddBorderTo.last);keepingTableFromBreakingAcrossPages();}
		}

		else if ((tableCount>=3)&&(tableCurrent==1)) {tableAddGridAndBoarders(tableAddBorderTo.first);}
		else if ((tableCount>=3)&&(tableCurrent!=1)&&(tableCount!=tableCurrent)) {tableAddGridAndBoarders(tableAddBorderTo.mid);}
		else if ((tableCount>=3)&&(tableCount==tableCurrent)) 
		{
			tableAddGridAndBoarders(tableAddBorderTo.last);
			keepingTableFromBreakingAcrossPages();	
		}
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
	
	@SuppressWarnings("unchecked")
	public void keepingTableFromBreakingAcrossPages() throws Exception 
	{
		for (Cell cell : (Iterable<Cell>)this.table.getChildNodes(NodeType.CELL,true))
		{
			cell.ensureMinimum();
			for (Paragraph para : cell.getParagraphs())
			{
				if (!(cell.getParentRow().isLastRow() && para.isEndOfCell()))
				{
					para.getParagraphFormat().setKeepWithNext(true);
				}
			}
		}
	}
}
