package org.openfuxml.renderer.word.content;



import java.util.Objects;

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
	
	@SuppressWarnings("unused")
	private Document doc;
	private DocumentBuilder builder;
	private Font font;

	public WordEmphasisRenderer(Document doc, DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	
	public void render(org.openfuxml.model.xml.core.text.Emphasis ofxEmphasis) throws OfxAuthoringException
	{
		font = builder.getFont();
		
		boolean bold = Objects.nonNull(ofxEmphasis.isBold()) && ofxEmphasis.isBold();
		boolean italic = Objects.nonNull(ofxEmphasis.isItalic()) && ofxEmphasis.isItalic();
		boolean underline = Objects.nonNull(ofxEmphasis.isUnderline()) && ofxEmphasis.isUnderline();
		boolean superscript = Objects.nonNull(ofxEmphasis.isSuperscript()) && ofxEmphasis.isSuperscript();
		boolean subscript = Objects.nonNull(ofxEmphasis.isSubscript()) && ofxEmphasis.isSubscript();
		
	//	boolean quote = ofxEmphasis.isSetQuote() && ofxEmphasis.isQuote();
		
		try
		{
			font.setBold(bold);
			font.setItalic(italic);
			if (underline) {font.setUnderline(Underline.SINGLE);}
			else {font.setUnderline(Underline.NONE);}
			font.setSuperscript(superscript);
			font.setSubscript(subscript);
			 
			builder.write(ofxEmphasis.getValue());
			font.setBold(false);
			font.setItalic(false);
			font.setUnderline(Underline.NONE);
			font.setSuperscript(false);
			font.setSubscript(false);
		}
		catch (Exception e)
		{}
	}
}
