package org.openfuxml.renderer.word.content;

import java.io.Serializable;

import org.openfuxml.renderer.docx.aspose.util.AsposeAlignmentUtil;
import org.openfuxml.renderer.docx.aspose.util.AsposeAlignmentUtil.setAlignmentEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphFormat;

public class WordTitleRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordTitleRenderer.class);

	private Document doc;
	private DocumentBuilder builder;
	
	public WordTitleRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	public void render(org.openfuxml.model.xml.core.ofx.Title ofxTitel)
	{
		//SetFont sF = new SetFont(doc, builder);
		//sF.setFont(setFontEnum.title);
		
		for (Serializable s : ofxTitel.getContent())
		{
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(0);
			AsposeAlignmentUtil sA = new AsposeAlignmentUtil(doc, builder);
			sA.setAlignment(setAlignmentEnum.left);
			paragraphFormat.setKeepTogether(true);
			builder.writeln(s.toString()+ ":");
		}
	}
}
