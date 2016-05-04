package org.openfuxml.renderer.word;

import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.AbstractOfxRenderer;
import org.openfuxml.renderer.word.structure.WordParagraphRenderer;
import org.openfuxml.renderer.word.structure.WordSectionRenderer;

/**
 * 
 * @author yannkruger
 *
 */
public class AbstractOfxWordRenderer extends AbstractOfxRenderer{
	
	protected XWPFDocument doc;
		
	public AbstractOfxWordRenderer(ConfigurationProvider cp){
		super(cp);
		doc = new XWPFDocument();
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
	
}
