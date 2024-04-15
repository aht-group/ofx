package org.openfuxml.renderer.docx.aspose.util;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;
import com.aspose.words.Underline;

public class OfxAsposeDocxParagraph
{
	public enum Alignment {left,right}
	
	private Alignment alignment;
	
	private double indentLeft;
	private double indentRight;
	
	public static OfxAsposeDocxParagraph instance() {return new OfxAsposeDocxParagraph();}
	
	private OfxAsposeDocxParagraph()
	{
		alignment = Alignment.left;
		indentLeft = 0;
		indentRight = 0;
		
	}
	
	public void save()
	{
		
	}
	
	public OfxAsposeDocxParagraph reset()
	{

		return this;
	}
	
	public OfxAsposeDocxParagraph alignment(Alignment value) {this.alignment=value; return this;}
	public OfxAsposeDocxParagraph indentLeft(double value) {this.indentLeft=value; return this;}
	public OfxAsposeDocxParagraph indentRight(double value) {this.indentRight=value; return this;}
	
	public void apply(DocumentBuilder builder)
	{
		ParagraphFormat format = builder.getParagraphFormat();
		
		switch(alignment)
		{
			case left: format.setAlignment(ParagraphAlignment.LEFT); break;
			case right: format.setAlignment(ParagraphAlignment.RIGHT); break;
		}
		
		format.setLeftIndent(indentLeft);
		format.setRightIndent(indentRight);
	}
	
}