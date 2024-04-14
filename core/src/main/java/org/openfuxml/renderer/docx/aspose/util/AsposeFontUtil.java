package org.openfuxml.renderer.docx.aspose.util;

import java.awt.Color;

import org.openfuxml.util.configuration.settings.word.OfxWordFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Font;
import com.aspose.words.Underline;

public class AsposeFontUtil
{
	final static Logger logger = LoggerFactory.getLogger(AsposeFontUtil.class);

	public enum setFontEnum {textheader, text, footer, title, image}
	Document doc;
	DocumentBuilder builder;
	private Font font;
	
	public AsposeFontUtil(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void setFont(setFontEnum preset)
	{
		font = builder.getFont();
		switch (preset)
		{
			case textheader:
				font.setSize(8);
		//		font.setLocaleId(id);
				font.setColor(Color.black);
				font.setBold(false);
				font.setName("Arial");
				font.setUnderline(Underline.NONE);
			break;
			case title:
				font.setSize(12);
		//		font.setLocaleId(id);
				font.setColor(Color.decode("#117732"));
				font.setBold(true);
				font.setName("Arial");
				font.setUnderline(Underline.THICK);
			break;
			case text:
				font.setSize(10.5);
	//			font.setLocaleId(id);
				font.setColor(Color.BLACK);
				font.setBold(false);
				font.setName("Arial");
				font.setUnderline(Underline.NONE);
			break;
			case footer:
				font.setSize(10.5);
	//			font.setLocaleId(id);
				font.setColor(Color.BLACK);
				font.setBold(true);
				font.setName("Arial");
				font.setUnderline(Underline.SINGLE);
			break;
			case image:
				font.setSize(6);
	//			font.setLocaleId(id);
				font.setColor(Color.BLACK);
				font.setBold(true);
				font.setName("Arial");
				font.setUnderline(Underline.NONE);
				font.setItalic(true);
			break;
			
		}
	}
	
	public static void apply(OfxWordFont font, DocumentBuilder builder)
	{
		Font f = builder.getFont();
		f.setName("Arial");
		f.setLocaleId(font.getDocxLocaleId());
		f.setSize(font.getSize());
		f.setBold(font.isBold());
		
		if(font.isUnderline()) {f.setUnderline(Underline.SINGLE);}
		else {f.setUnderline(Underline.NONE);}
		
		f.setColor(font.getColor());
		f.setColor(Color.green);
	}
}