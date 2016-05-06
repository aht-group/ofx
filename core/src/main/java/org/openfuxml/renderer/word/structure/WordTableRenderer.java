package org.openfuxml.renderer.word.structure;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Body;
import org.openfuxml.content.table.Cell;
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
		
		if (table.isSetSpecification()){t = renderSpecification(t, table.getSpecification());}
		if (table.getContent().isSetHead()){t = renderHeadline(t, table.getContent().getHead(), doc);}
		if (table.getContent().isSetBody()){t = renderBody(t, table.getContent().getBody(), doc);}
		
		XWPFParagraph p = t.getRow(0).getCell(0).getParagraphs().get(0);
		p.createRun();
		
		return doc;
	}
	
	private XWPFTable renderSpecification(XWPFTable t, Specification s){
		//TODO Attribute der Tablle hinzufügen 				
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
					
					if(o instanceof String){	
						t.getRow(z).getCell(s).setText((String)o);
						z++;
						
						//Erstellt neue Zelle
						if(c.getContent().size() >= z){
							t.getRow(z).addNewTableCell();
						}
					}
				}
			}
		}
		
		return t;
	}
	
	private XWPFTable renderBody(XWPFTable t, List<Body> b, XWPFDocument doc){
		int z = 0;
		int s = 0;
		
		for(Body body : b)
		{
			for(Row r : body.getRow())
			{	
				for(Cell c : r.getCell())
				{
					for(Object o : c.getContent()){
						if(o instanceof String){	
							t.getRow(z).getCell(s).setText((String)o);
							z++;
							
							//Erstellt neue Zelle
							if(c.getContent().size() >= z){
								t.getRow(z).addNewTableCell();
							}
						}
						else if (o instanceof Paragraph){
							logger.info("");
							t.getRow(z).getCell(s).setText(((Paragraph)o).getContent().toString());
							
							//TODO Neue Zelle erstellen... 
						}
					}
				}
			//Durchlaufen der Tabelle
			s++;
			z=0;
			}
		}
		
		return t;
	}

	
	
	
}
