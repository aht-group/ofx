package org.openfuxml.renderer.word.structure;


import java.io.Serializable;

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
		for (Serializable s : ofxParagraph.getContent())
		{
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(12);
			paragraphFormat.setAlignment(1);
			paragraphFormat.setKeepTogether(true);
			builder.write(s.toString());
			logger.debug(s.toString());
		}

	}
}
