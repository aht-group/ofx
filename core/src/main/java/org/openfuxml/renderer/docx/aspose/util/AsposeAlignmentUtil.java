package org.openfuxml.renderer.docx.aspose.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;

public class AsposeAlignmentUtil
{
	public enum setAlignmentEnum {left, center, rightIndentR70, rightIndentR5}
	
	final static Logger logger = LoggerFactory.getLogger(AsposeAlignmentUtil.class);

	Document doc;
	DocumentBuilder builder;

	private ParagraphFormat paragraphFormat;
	
	public AsposeAlignmentUtil(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}
	
	public void setAlignment(setAlignmentEnum preset)
	{
		paragraphFormat = builder.getParagraphFormat();
		switch (preset)
		{
			case left:
				paragraphFormat.setAlignment(ParagraphAlignment.LEFT);
				paragraphFormat.setLeftIndent(0);
				paragraphFormat.setFirstLineIndent(0);
				paragraphFormat.setRightIndent(0);
			break;
			case center:
				paragraphFormat.setAlignment(ParagraphAlignment.CENTER);
				paragraphFormat.setLeftIndent(0);
				paragraphFormat.setRightIndent(0);
			break;
			case rightIndentR70:
				paragraphFormat.setAlignment(ParagraphAlignment.RIGHT);
				paragraphFormat.setLeftIndent(0);
				paragraphFormat.setRightIndent(70);
			break;
			case rightIndentR5:
				paragraphFormat.setAlignment(ParagraphAlignment.RIGHT);
				paragraphFormat.setLeftIndent(0);
				paragraphFormat.setRightIndent(5);
			break;
		}
	}

}
