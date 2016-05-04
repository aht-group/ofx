package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;

public class WordParagraphRenderer extends AbstractOfxWordRenderer {

	public WordParagraphRenderer(ConfigurationProvider cp) {
		super(cp);
	}
	
	/**
	 * Erstellt einen leeren Paragraphen am Anfang, für alle weiteren Informationen
	 * @param doc
	 * @param paragraph
	 * @return
	 */
	public XWPFDocument renderer(XWPFDocument doc, Paragraph paragraph){
		
		XWPFParagraph p = doc.createParagraph();
		
		for (Object o : paragraph.getContent()){
			if(o instanceof Paragraph){
				renderParagraph(doc,(Paragraph)o);
			}
			else if(o instanceof String){
				XWPFRun r = p.createRun();
		        r.setTextPosition(-10);
		        r.setText((String)o);
			}
		}
		
		return doc;
	}

}
