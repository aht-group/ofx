package org.openfuxml.renderer.word.content;



import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.Underline;

public class WordEmphasisRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordEmphasisRenderer.class);
	
	Document doc;
	DocumentBuilder builder;
	private Font font;

	public WordEmphasisRenderer(Document doc, DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	
	public void render(org.openfuxml.content.text.Emphasis ofxEmphasis) throws OfxAuthoringException
	{
		font = builder.getFont();
		
		boolean bold = ofxEmphasis.isSetBold() && ofxEmphasis.isBold();
		boolean italic = ofxEmphasis.isSetItalic() && ofxEmphasis.isItalic();
		boolean underline = ofxEmphasis.isSetUnderline() && ofxEmphasis.isUnderline();
		
		try
		{
			if (bold) {font.setBold(true);}
			else if (!bold) {font.setBold(false);}
			if (italic) {font.setItalic(true);}
			else if (!italic) {font.setItalic(false);}
			if (underline) {font.setUnderline(Underline.SINGLE);}
			else if (!underline) {font.setUnderline(Underline.NONE);}
		}
		catch (Exception e)
		{}
			
	}
}
