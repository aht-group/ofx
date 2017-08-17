package org.openfuxml.renderer.word.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Shape;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;

public class WordImageRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordImageRenderer.class);

	Document doc;
	DocumentBuilder builder;
	Shape shape;
	List<String> txt = new ArrayList<String>();

	public WordImageRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void render(org.openfuxml.content.media.Image ofxImage)
	{
		logger.trace("WordImageRenderer.render()");
		SetFont sF = new SetFont(doc, builder);
		sF.setFont(setFontEnum.image);
		try
		{
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
			paragraphFormat.setSpaceAfter(2);
			paragraphFormat.setKeepTogether(true);
			shape = builder.insertImage(ofxImage.getMedia().getSrc());
			shape.setWidth(ofxImage.getWidth().getValue());
			if (ofxImage.getWidth().isFlex() == true)
			{
				shape.setAspectRatioLocked(true);
			}
			else
			{
				shape.setAspectRatioLocked(false);
			}

			shape.setHeight(ofxImage.getHeight().getValue());

			if (ofxImage.getAlignment().getHorizontal() == "center")
			{
				logger.debug("center");
				shape.setHorizontalAlignment(ParagraphAlignment.CENTER);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "left")
			{
				logger.debug("left");
				shape.setHorizontalAlignment(ParagraphAlignment.LEFT);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "right")
			{
				logger.debug("right");
				shape.setHorizontalAlignment(ParagraphAlignment.RIGHT);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "")
			{
				logger.debug("-----");
				shape.setHorizontalAlignment(ParagraphAlignment.CENTER);
			}
			builder.writeln();	
			sF.setFont(setFontEnum.image);
			builder.writeln(ofxImage.getTitle().getContent().get(0).toString());	
					}
		catch (Exception e)
		{
			logger.error("WordImageRenderer.render()");
		}
	}
}