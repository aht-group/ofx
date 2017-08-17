package org.openfuxml.renderer.word.content;


import java.io.Serializable;

import org.openfuxml.renderer.word.util.SetAlignment;
import org.openfuxml.renderer.word.util.SetAlignment.setAlignmentEnum;
import org.openfuxml.renderer.word.util.SetFont;

import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphFormat;


public class WordParagraphRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(WordParagraphRenderer.class);

	Document doc;
	DocumentBuilder builder;
	
	public WordParagraphRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}
	
	public void render(org.openfuxml.content.ofx.Paragraph ofxParagraph)
	{
		logger.trace("WordParagraphRenderer.render()");
		SetFont sF = new SetFont(doc, builder);
		sF.setFont(setFontEnum.text);
		
		for (Serializable s : ofxParagraph.getContent())
		{
			SetAlignment sA = new SetAlignment(doc, builder);
			sA.setAlignment(setAlignmentEnum.left);
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(0);
			
			paragraphFormat.setKeepTogether(true);
			builder.writeln(s.toString());

			logger.debug(s.toString());
		}

	}
}
