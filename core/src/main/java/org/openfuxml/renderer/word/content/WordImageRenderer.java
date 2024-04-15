package org.openfuxml.renderer.word.content;

import java.awt.Color;
import java.io.File;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;
import com.aspose.words.Shape;
import com.aspose.words.Underline;

public class WordImageRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordImageRenderer.class);

	private DocumentBuilder builder;
	private Shape shape;

	public WordImageRenderer(DocumentBuilder builder) {this.builder=builder;}

	public void render(org.openfuxml.model.xml.core.media.Image ofxImage) throws Exception
	{
		File f = new File(ofxImage.getMedia().getSrc());
		if (f.exists())
		{
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
			paragraphFormat.setSpaceAfter(4);
			paragraphFormat.setKeepTogether(true);

			// insert image..
			shape = builder.insertImage(ofxImage.getMedia().getSrc());

			// set aspect ratio..
			if (ofxImage.getWidth().isFlex() == true)
			{
				shape.setAspectRatioLocked(true);
			}
			else
			{
				shape.setAspectRatioLocked(false);
			}

			// with Unit..
			if (Objects.nonNull(ofxImage.getWidth().getUnit()))
			{
				if (ofxImage.getWidth().getUnit() == "percentage")
				{
					shape.setWidth((shape.getWidth() * ofxImage.getWidth().getValue()) / 100);
					shape.setHeight((shape.getHeight() * ofxImage.getHeight().getValue()) / 100);
				}
				// to do /*if (ofxImage.getWidth().getUnit() == "relative"){}*/
			}
			// without Unit...
			else
			{
				shape.setWidth(ofxImage.getWidth().getValue());
				shape.setHeight(ofxImage.getHeight().getValue());
			}

			// set alignment..
			if (ofxImage.getAlignment().getHorizontal() == "center")
			{
				shape.setHorizontalAlignment(ParagraphAlignment.CENTER);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "left")
			{
				shape.setHorizontalAlignment(ParagraphAlignment.LEFT);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "right")
			{
				shape.setHorizontalAlignment(ParagraphAlignment.RIGHT);
			}
			else if (ofxImage.getAlignment().getHorizontal() == "")
			{
				shape.setHorizontalAlignment(ParagraphAlignment.CENTER);
			}

			// write empty line..
			builder.writeln();

			
			Font font = builder.getFont();
			font.setSize(6);
			font.setColor(Color.BLACK);
			font.setBold(true);
			font.setName("Arial");
			font.setUnderline(Underline.NONE);
			font.setItalic(true);
			
			builder.writeln(ofxImage.getTitle().getContent().get(0).toString());
		}
		else
		{
			logger.error("missing image file in path: "+ofxImage.getMedia().getSrc());
		}
	}
}
