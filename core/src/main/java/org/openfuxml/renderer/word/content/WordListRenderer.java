package org.openfuxml.renderer.word.content;

import java.awt.Color;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.LineStyle;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.PreferredWidth;

public class WordListRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordListRenderer.class);

	private Document doc;
	private DocumentBuilder builder;
	boolean numbersFormating;
	
	//...make my own list....
	String makeItSelf 	= "• ";
	String makeItSelf2 	= "		";


	
	public WordListRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.model.xml.core.list.List ofxList, boolean numbersFormating) 
	{
		this.numbersFormating=numbersFormating;
		render(ofxList); 
	} 
	
	public void render(org.openfuxml.model.xml.core.list.List ofxList) 
	{
		SetFont sF = new SetFont(doc, builder);sF.setFont(setFontEnum.text);

		com.aspose.words.Table table = builder.startTable();
		builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(10));
        
		//for each entry..
		int i = 0;
		for (Object o : ofxList.getItem())
		{	
			if (o instanceof Item) 
			{
				for (Object o2 : ((Item) o).getContent())
				{	
					if (o2 instanceof Paragraph)
					{
						try
						{
							builder.insertCell();
							builder.getParagraphFormat().setAlignment(ParagraphAlignment.LEFT);
						
							if (numbersFormating) {builder.write(i+1 + ".");}
							else if (!numbersFormating) {builder.write(makeItSelf);}
							builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(2));
							builder.insertCell();
							builder.getParagraphFormat().setAlignment(ParagraphAlignment.JUSTIFY);
							paragraphRenderer((Paragraph)o2);
							builder.getCellFormat().setPreferredWidth(PreferredWidth.fromPercent(98));
							builder.endRow();
						} catch (OfxAuthoringException e) {}
						i++;
					}
				}
			} 
		}
		try{table.setTopPadding(3);		table.setBorders(LineStyle.NONE, 0.0, Color.BLACK);}catch(Exception e2){}
		builder.endTable();
	}
	
	private void paragraphRenderer(org.openfuxml.content.ofx.Paragraph s) throws OfxAuthoringException
	{
		WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
		wPF.render(s,1,1);
	}
}
