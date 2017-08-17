package org.openfuxml.renderer.word.content;

import java.io.Serializable;

import org.openfuxml.renderer.word.util.SetAlignment;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetAlignment.setAlignmentEnum;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

public class WordListRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordListRenderer.class);

	Document doc;
	DocumentBuilder builder;
	
	//...make my own list....
	String makeItSelf 	= "• ";
	String makeItSelf2 	= "  ";
	
	public WordListRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.content.list.List ofxList)
	{
		SetFont sF = new SetFont(doc, builder);
		
		//for each entry..
		for (org.openfuxml.content.list.Item item : ofxList.getItem())
		{
			//add name..
			if (item.isSetName()==true)
			{	
				sF.setFont(setFontEnum.text);
				SetAlignment sA = new SetAlignment(doc, builder);
				sA.setAlignment(setAlignmentEnum.left);
				
				builder.write(makeItSelf+item.getName()+": ");
			}
			//add text item..
			if (item.isSetContent() == true)
			{
				sF.setFont(setFontEnum.text);
				SetAlignment sA = new SetAlignment(doc, builder);
				sA.setAlignment(setAlignmentEnum.left);
				
				builder.writeln(makeItSelf2+item.getContent().get(0).toString());
			}
		}
	}
}
