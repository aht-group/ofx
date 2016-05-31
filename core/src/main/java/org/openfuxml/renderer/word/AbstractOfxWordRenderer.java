package org.openfuxml.renderer.word;

import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.openfuxml.renderer.word.structure.WordDocumentRenderer;
import org.openfuxml.renderer.word.structure.WordParagraphRenderer;
import org.openfuxml.renderer.word.structure.WordSectionRenderer;
import org.openfuxml.renderer.word.structure.WordTableRenderer;
import org.openfuxml.renderer.word.structure.WordTitleRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;

/**
 * 
 * @author yannkruger
 *
 */
public class AbstractOfxWordRenderer extends AbstractOfxRenderer{
	
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxWordRenderer.class);
	protected XWPFDocument doc;
		
	public AbstractOfxWordRenderer(ConfigurationProvider cp){
		super(cp);
		doc = new XWPFDocument();
//		initLogger();
	}
	
	/**
	 * initializieren des loggers 
	 */
	public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.addAltPath("config.ofx-util.test");
		loggerInit.init();
    }
	
	public XWPFDocument getDocument(){return this.doc;}
		
	/**
	 * creates new .docx file with current content
	 * @param doc
	 * @throws Exception
	 */
	public void writeDocument(XWPFDocument doc) throws Exception{
		FileOutputStream out = new FileOutputStream("target/docx/TestCV.docx");
        doc.write(out);
        out.close();
	}
	
	/**
	 * responsible for section rendering,
	 * @param doc
	 * @param section
	 */
	public void renderSection(XWPFDocument doc, Section section, int lvl){
		WordSectionRenderer sectionRenderer = new WordSectionRenderer(cp, lvl);
		this.doc = sectionRenderer.renderer(doc, section);		
	}
	
	/**
	 * responsible for paragraph rendering
	 * @param doc
	 * @param paragraph
	 */
	public void renderParagraph(XWPFDocument doc, Paragraph paragraph){
		WordParagraphRenderer paragraphRenderer = new WordParagraphRenderer(cp);
		this.doc = paragraphRenderer.renderer(doc, paragraph);
	} 
	
	/**
	 * responsible for title rendering
	 * @param doc
	 * @param title
	 * @param lvl
	 */
	public void renderTitle(XWPFDocument doc, Title title, int lvl){
		WordTitleRenderer titleRenderer = new WordTitleRenderer(cp);
		this.doc = titleRenderer.renderer(doc, title, lvl);
	}
	/**
	 * responsible for the Table rendering
	 * @param doc
	 * @param t
	 */
	public void renderTable(XWPFDocument doc, Table t){
		WordTableRenderer tableRenderer = new WordTableRenderer(cp);
		this.doc = tableRenderer.renderer(doc, t);
	}
	
	public void renderDocument(XWPFDocument doc, Document docOfx){
		WordDocumentRenderer documentRenderer = new WordDocumentRenderer(cp);
		this.doc = documentRenderer.renderer(doc, docOfx);
	}
	
}
