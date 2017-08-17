package org.openfuxml.renderer.word.content;

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

	public WordImageRenderer(Document doc, DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.content.media.Image ofxImage) throws Exception
	{
		SetFont sF = new SetFont(doc, builder);
		ParagraphFormat paragraphFormat = builder.getParagraphFormat();
		paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
		paragraphFormat.setSpaceAfter(4);
		paragraphFormat.setKeepTogether(true);
		
		// insert image..
		shape = builder.insertImage(ofxImage.getMedia().getSrc());
		
		//set aspect ratio..
		if (ofxImage.getWidth().isFlex()==true){shape.setAspectRatioLocked(true);}else{shape.setAspectRatioLocked(false);}
		
		//with Unit..
		if (ofxImage.getWidth().isSetUnit()==true)
		{
			if (ofxImage.getWidth().getUnit()=="percentage")
			{
				shape.setWidth((shape.getWidth() * ofxImage.getWidth().getValue()) / 100);
				shape.setHeight((shape.getHeight() * ofxImage.getHeight().getValue()) / 100);
			}	
//to do			/*if (ofxImage.getWidth().getUnit() == "relative"){}*/				
		}
		//without Unit...
		else
		{
			shape.setWidth(ofxImage.getWidth().getValue());
			shape.setHeight(ofxImage.getHeight().getValue());
		}
		
		//set alignment..
		if (ofxImage.getAlignment().getHorizontal()=="center"){shape.setHorizontalAlignment(ParagraphAlignment.CENTER);}
		else if (ofxImage.getAlignment().getHorizontal()=="left"){shape.setHorizontalAlignment(ParagraphAlignment.LEFT);}
		else if (ofxImage.getAlignment().getHorizontal()=="right"){shape.setHorizontalAlignment(ParagraphAlignment.RIGHT);}
		else if (ofxImage.getAlignment().getHorizontal()==""){shape.setHorizontalAlignment(ParagraphAlignment.CENTER);}
		
		//write empty line..
		builder.writeln();
		
		//set font and write image title...
		sF.setFont(setFontEnum.image);
		builder.writeln(ofxImage.getTitle().getContent().get(0).toString());
	}
}
