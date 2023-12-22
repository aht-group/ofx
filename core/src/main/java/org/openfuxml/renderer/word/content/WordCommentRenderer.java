package org.openfuxml.renderer.word.content;

import java.util.Date;

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
	
	private Document doc;
	private DocumentBuilder builder;
	
	public WordCommentRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.model.xml.core.ofx.Comment ofxComment)
	{		
		for (org.openfuxml.model.xml.core.ofx.Raw raw : ofxComment.getRaw())
		{
			//"XXX" ..for "Author" and "Initial" 
			Comment comment = new Comment(doc, "XXX", "XXX", new Date());
			builder.getCurrentParagraph().appendChild(comment);
			comment.getParagraphs().add(new Paragraph(doc));
			comment.getFirstParagraph().getRuns().add(new Run(doc,raw.getValue()));
		}
	}
}
