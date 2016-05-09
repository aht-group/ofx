package org.openfuxml.renderer.word.structure;

import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Column;
import org.openfuxml.content.table.Head;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordTableRenderer extends AbstractOfxWordRenderer{

	final static Logger logger = LoggerFactory.getLogger(WordTitleRenderer.class);
		
	
	public WordTableRenderer(ConfigurationProvider cp) {
		super(cp);
	}
	
	/**
	 * Specification: Tabellen eigenschaften
	 * Head: Überschriften
	 * Body: Inhalt
	 * 
	 * @param doc
	 * @param table
	 * @return doc
	 */
	public XWPFDocument renderer(XWPFDocument doc, Table table){
		
		XWPFTable t = doc.createTable();
		//entfernen der Tabellen umrandung
		t.getCTTbl().getTblPr().unsetTblBorders();
		
		if (table.isSetSpecification()){t = renderSpecification(t, table.getSpecification(), doc);}
		if (table.getContent().isSetHead()){t = renderHeadline(t, table.getContent().getHead(), doc);}
		if (table.getContent().isSetBody()){t = renderBody(t, table.getContent().getBody(), doc);}
		
		XWPFParagraph p = t.getRow(0).getCell(0).getParagraphs().get(0);
		p.createRun();
		
		return doc;
	}
	
	/**
	 * Rendert die Spezifikationen pro Spalte
	 * @param t
	 * @param sp
	 * @param doc
	 * @return
	 */
	private XWPFTable renderSpecification(XWPFTable t, Specification sp, XWPFDocument doc){
		int z = 0;
		int s = 0;
		
		
		for (XWPFTableRow row : t.getRows()){
			
//			Table Row Properties, erstmal nicht wichtig
//			CTTrPr trPr = row.getCtRow().addNewTrPr();
									
				for(Object o : sp.getColumns().getColumn())
				{
									
					if(o instanceof Column){	
						
						if (((Column)o).isSetAlignment()){
							XWPFParagraph para = row.getCell(s).getParagraphs().get(0);
							if (((Column)o).getAlignment().getHorizontal().equals("center")){
								para.setAlignment(ParagraphAlignment.CENTER);
							}
							if (((Column)o).getAlignment().getHorizontal().equals("left")){
								para.setAlignment(ParagraphAlignment.LEFT);
							}
							if (((Column)o).getAlignment().getHorizontal().equals("right")){
								para.setAlignment(ParagraphAlignment.RIGHT);
							}
						}
						if (((Column)o).isSetWidth()){
							//Seitenbreite durch angebene % der Specification
							//20th of a point w ="11906" h ="16838"
					        	double percent = ((Column)o).getWidth().getValue();
					        	double col_width = (11906/100) * percent;
					        	row.getCell(s).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf((long) col_width));
					        }
						}
						
					}
					//neue Zelle erstellen, cell size zum vergleich
					if(t.getRow(0).getTableCells().size() != sp.getColumns().getColumn().size()){
						t.getRow(z).addNewTableCell();
					}
					
					s++;
				}
					
		return t;
	}
	
	/**
	 * Rendert die Überschrift der Tabelle, prüft ob es einen Nachfolger für eine weitere Spalte gibt
	 * @param o
	 * @return
	 */
	private XWPFTable renderHeadline(XWPFTable t, Head h, XWPFDocument doc){
		int z = 0;
		int s = 0;
		
		for(Row r : h.getRow())
		{
			for(Cell c : r.getCell())
			{
				for(Object o : c.getContent()){
					
					if (o instanceof Paragraph){
						t.getRow(z).getCell(s).setText(((Paragraph)o).getContent().toString());
						s++;
						//Neue Zelle erstellen... 
						if(t.getRow(0).getTableCells().size() < r.getCell().size()){
							t.getRow(z).addNewTableCell();
						}
					}
				}
			}
		
		//Durchlaufen der Tabelle und neue Reihe erstellen
		z++;
		s=0;
		if(h.getRow().size() >= z){
			t.createRow();
			//Rendern mehrerer Reihen 
			//renderSpecification(t, table.getSpecifications, doc);
		}
			
		}
		
		return t;
	}
	
	/**
	 * Rendert die Tabellen inhalte
	 * @param t
	 * @param b
	 * @param doc
	 * @return
	 */
	private XWPFTable renderBody(XWPFTable t, List<Body> b, XWPFDocument doc){
		int z = 1; // 0 = Head, 1 = body
		int s = 0;
		
		for(Body body : b)
		{
//			logger.info("body");
			for(Row r : body.getRow())
			{	
//				logger.info("row");
				for(Cell c : r.getCell())
				{
//					logger.info("cell");
					for(Object o : c.getContent()){
						
						 if (o instanceof Paragraph){
//							logger.info("Paragraph");
//							logger.info("row z: " + z + " cell s = " + s);
							t.getRow(z).getCell(s).setText(((Paragraph)o).getContent().toString());
							s++;
							//Neue Zelle erstellen... 
							if(t.getRow(0).getTableCells().size() < r.getCell().size()){
//								logger.info("New table Cell");
								t.getRow(z).addNewTableCell();
							}
						}
					}
				}
				
				//Durchlaufen der Tabelle und erstellen einer neuen Zeile
				z++;
				s=0;
				
				if(body.getRow().size() >= t.getRows().size()){
//					logger.info("body row created");
					t.createRow();
				}
				
			}
		}
		
		return t;
	}

	
	
	
}
