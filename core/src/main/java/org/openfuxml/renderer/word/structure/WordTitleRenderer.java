package org.openfuxml.renderer.word.structure;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.word.AbstractOfxWordRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordTitleRenderer extends AbstractOfxWordRenderer{

	final static Logger logger = LoggerFactory.getLogger(WordTitleRenderer.class);
		
	
	public WordTitleRenderer(ConfigurationProvider cp) {
		super(cp);
	}
	
	/**
	 * Paragraph wird erst im instance of String erzeugt, damit keine leeren Paragraphen entstehen, lvl entspricht den Überschriften
	 * @param doc
	 * @param title
	 * @param lvl
	 * @return doc
	 */
	public XWPFDocument renderer(XWPFDocument doc, Title title, int lvl){

		for (Object o : title.getContent()){
			if(o instanceof String){
				XWPFParagraph p = doc.createParagraph();
				XWPFRun run = p.createRun();
				run.setText((String)o);
				run.setBold(true);
					if (lvl <= 1){run.setFontSize(28);}
					else if(lvl == 2){run.setFontSize(16);}
					else if(lvl == 3){run.setFontSize(13);}
					else if(lvl == 4){run.setFontSize(12);}
					else if(lvl >= 5){run.setFontSize(11);}
				}
			else if(o instanceof Title){renderTitle(doc, (Title)o, lvl++);}
			else{logger.info("Kein logger für " + o.getClass().getSimpleName());}
		}
		
		return doc;
	} 
	

}
