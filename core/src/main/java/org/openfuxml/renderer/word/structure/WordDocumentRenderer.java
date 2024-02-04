package org.openfuxml.renderer.word.structure;

import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.model.xml.core.ofx.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

public class WordDocumentRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(WordDocumentRenderer.class);
	
	public Document doc;
	public DocumentBuilder builder;

	public WordDocumentRenderer()
	{
		try
		{
			doc = new Document();
		} catch (Exception e)
		{}
		;
		builder = new DocumentBuilder(doc);
	}

	public Document getDoc(){return doc;}

	public DocumentBuilder getBuilder(){return builder;}



	public Document render(Content ofxContent) throws Exception
	{
//		lvl = 0;
		//see XML content..
		 JaxbUtil.info(ofxContent);
		
		for (Object s : ofxContent.getContent())
		{		
			if (s instanceof org.openfuxml.model.xml.core.ofx.Section)
			{	
				renderSection((org.openfuxml.model.xml.core.ofx.Section) s);
			}
		}
		builder.moveToDocumentEnd();
		
		//for future..
		//builder.insertBreak(BreakType.PAGE_BREAK); 
	
		return doc;
	}

	private void renderSection(org.openfuxml.model.xml.core.ofx.Section ofxSection) throws Exception 
	{
		WordSectionRenderer sf = new WordSectionRenderer(doc,builder);
		sf.renderWithException(ofxSection);
	}
}
