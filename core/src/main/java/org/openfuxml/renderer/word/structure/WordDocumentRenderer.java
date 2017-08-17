package org.openfuxml.renderer.word.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.util.OfxDocumentStructureVerifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.BreakType;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.PageSetup;
import com.aspose.words.Section;

import net.sf.exlp.util.xml.JaxbUtil;

public class WordDocumentRenderer
{	
	final static Logger logger = LoggerFactory.getLogger(WordDocumentRenderer.class);
	
	private int localeId;
	public Document doc;
	public DocumentBuilder builder;
	private int lvl;


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
	private enum setLocalId	{de,en,fr}

	public Document getDoc(){return doc;}

	public DocumentBuilder getBuilder(){return builder;}



	public Document render(Content ofxContent) throws Exception
	{
		lvl = 0;
		//see XML content..
		// JaxbUtil.info(ofxContent);
		
		for (Object s : ofxContent.getContent())
		{		
			if (s instanceof org.openfuxml.content.ofx.Section)
			{	
				renderSection((org.openfuxml.content.ofx.Section) s);
			}
		}
		builder.moveToDocumentEnd();
		
		//for future..
		//builder.insertBreak(BreakType.PAGE_BREAK); 
	
		return doc;
	}

	private void renderSection(org.openfuxml.content.ofx.Section ofxSection) throws Exception 
	{
		WordSectionRenderer sf = new WordSectionRenderer(doc,builder);
		sf.render(ofxSection);
	}
}
