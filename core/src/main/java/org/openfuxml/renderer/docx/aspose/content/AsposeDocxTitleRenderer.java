package org.openfuxml.renderer.docx.aspose.content;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.DocumentBuilder;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.ParagraphFormat;

public class AsposeDocxTitleRenderer
{
	final static Logger logger = LoggerFactory.getLogger(AsposeDocxTitleRenderer.class);

	private DocumentBuilder builder;
	
	public AsposeDocxTitleRenderer(DocumentBuilder builder) {this.builder=builder;}
	
	public void render(org.openfuxml.model.xml.core.ofx.Title ofxTitel)
	{
		//SetFont sF = new SetFont(doc, builder);
		//sF.setFont(setFontEnum.title);
		
		for (Serializable s : ofxTitel.getContent())
		{
			ParagraphFormat paragraphFormat = builder.getParagraphFormat();
			paragraphFormat.setFirstLineIndent(0);
			paragraphFormat.setAlignment(ParagraphAlignment.LEFT);
			paragraphFormat.setLeftIndent(0);
			paragraphFormat.setRightIndent(0);
				
			paragraphFormat.setKeepTogether(true);
			builder.writeln(s.toString()+ ":");
		}
	}
}
