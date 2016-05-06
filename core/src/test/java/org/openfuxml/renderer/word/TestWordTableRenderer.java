package org.openfuxml.renderer.word;

import java.io.FileOutputStream;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.word.structure.WordTableRenderer;
import org.openfuxml.renderer.word.structure.WordTitleRenderer;

import net.sf.exlp.util.xml.JaxbUtil;

public class TestWordTableRenderer extends AbstractTestWordRenderer {
	
public static void main(String[] args) throws Exception{
	
	TestWordTableRenderer twtr = new TestWordTableRenderer();
	WordTableRenderer wtr = new WordTableRenderer(new ConfigurationProvider() {
		
		@Override
		public DefaultSettingsManager getDefaultSettingsManager() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public CrossMediaManager getCrossMediaManager() {
			// TODO Auto-generated method stub
			return null;
		}
	});	
	
	//Testtabelle
	Table t = twtr.buildTable(); 
		
	JaxbUtil.info(t);
//	createSimpleTable();
	wtr.renderTable(wtr.getDocument(), t);
	wtr.writeDocument(wtr.getDocument());
	}

public static void createSimpleTable() throws Exception {
    XWPFDocument doc = new XWPFDocument();

    XWPFTable table = doc.createTable(3, 3);

    table.getRow(1).getCell(1).setText("EXAMPLE OF TABLE");

    // table cells have a list of paragraphs; there is an initial
    // paragraph created when the cell is created. If you create a
    // paragraph in the document to put in the cell, it will also
    // appear in the document following the table, which is probably
    // not the desired result.
    XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);

    XWPFRun r1 = p1.createRun();
    r1.setBold(true);
    r1.setText("The quick brown fox");
    r1.setItalic(true);
    r1.setFontFamily("Courier");
    r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
    r1.setTextPosition(100);

    table.getRow(2).getCell(2).setText("only text");

    FileOutputStream out = new FileOutputStream("target/docx/TestCV.docx");
    doc.write(out);
    out.close();
    
    doc.close();
}
}
