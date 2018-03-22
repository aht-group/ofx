package org.openfuxml.renderer.word.content;

import java.io.Serializable;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.word.util.SetAlignment;
import org.openfuxml.renderer.word.util.SetFont;
import org.openfuxml.renderer.word.util.SetAlignment.setAlignmentEnum;
import org.openfuxml.renderer.word.util.SetFont.setFontEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.ListTemplate;

public class WordListRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordListRenderer.class);

	private Document doc;
	private DocumentBuilder builder;
	
	//...make my own list....
	String makeItSelf 	= "• ";
	String makeItSelf2 	= "		";
	
	public WordListRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}

	public void render(org.openfuxml.content.list.List ofxList) 
	{
		SetFont sF = new SetFont(doc, builder);
		builder.write(makeItSelf );
		
		builder.getListFormat().setList(doc.getLists().add(ListTemplate.NUMBER_ARABIC_DOT));
		builder.getListFormat().setListLevelNumber(1);
		
		//for each entry..
		for (Object o : ofxList.getItem())
		{	
			
				if (o instanceof Item) 
				{
					for (Object o2 : ((Item) o).getContent())
					{	
						if (o2 instanceof Paragraph)
						{
							try {
								paragraphRenderer((Paragraph)o2);
							} catch (OfxAuthoringException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				} 
			}
		
		builder.getListFormat().setList(null);
		
		}
	
	private void paragraphRenderer(org.openfuxml.content.ofx.Paragraph s) throws OfxAuthoringException
	{
		WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
		wPF.render(s,0,1);
	}
}
