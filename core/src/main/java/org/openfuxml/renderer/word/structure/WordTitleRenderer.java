package org.openfuxml.renderer.word.structure;

import java.io.Serializable;

import org.openfuxml.renderer.word.util.SetAlignment;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetAlignment.setAlignmentEnum;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphFormat;

public class WordTitleRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordTitleRenderer.class);

	Document doc;
	DocumentBuilder builder;
	
	public WordTitleRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}
	
	public void render(org.openfuxml.content.ofx.Title ofxTitel)
	{
		SetFont sF = new SetFont(doc, builder);
		sF.setFont(setFontEnum.title);
		
		for (Serializable s : ofxTitel.getContent())
		{

			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(12);
			SetAlignment sA = new SetAlignment(doc, builder);
			sA.setAlignment(setAlignmentEnum.center);
			paragraphFormat.setKeepTogether(true);
			builder.writeln(s.toString()+ ":");
			logger.debug(s.toString()+ ":");
		}
		
	}
}
