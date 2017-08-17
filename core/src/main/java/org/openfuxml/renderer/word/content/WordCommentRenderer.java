package org.openfuxml.renderer.word.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Comment;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Paragraph;
import com.aspose.words.Run;

public class WordCommentRenderer 
{
	final static Logger logger = LoggerFactory.getLogger(WordCommentRenderer.class);
	
	Document doc;
	DocumentBuilder builder;
	List<String> txt = new ArrayList<String>();
	
	public WordCommentRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void render(org.openfuxml.content.ofx.Comment ofxComment)
	{		
		logger.trace("WordCommentRenderer.render()");
		for (org.openfuxml.content.ofx.Raw raw : ofxComment.getRaw())
		{
			Comment comment = new Comment(doc, "XXX", "XX", new Date());
			builder.getCurrentParagraph().appendChild(comment);
			comment.getParagraphs().add(new Paragraph(doc));
			comment.getFirstParagraph().getRuns().add(new Run(doc,raw.getValue()));
			logger.debug(raw.getValue());
		}
	}
}
