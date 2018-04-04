package org.openfuxml.renderer.word.structure;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.word.content.WordCommentRenderer;
import org.openfuxml.renderer.word.content.WordImageRenderer;
import org.openfuxml.renderer.word.content.WordListRenderer;
import org.openfuxml.renderer.word.content.WordParagraphRenderer;
import org.openfuxml.renderer.word.content.WordTableRenderer;
import org.openfuxml.renderer.word.content.WordTitleRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordSectionRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordSectionRenderer.class);

	private Document doc;
	private DocumentBuilder builder;
	private int tableCount=0;
	private int tableCurrent=0;
	private int paragraphCount=0;
	private boolean listNumbersFormating=false;
	private int paragraphCurrent;

	public WordSectionRenderer(Document doc,DocumentBuilder builder){this.doc=doc;this.builder=builder;}
	
	public void render(org.openfuxml.content.ofx.Section ofxSection, boolean listNumbersFormating)
	{
		this.listNumbersFormating=listNumbersFormating;
		try {render(ofxSection);} catch (Exception e) {}
	}
	
	public void render(org.openfuxml.content.ofx.Section ofxSection) throws Exception
	{
		//Section sectionToAdd = new Section(doc);

		// Comment always on top!
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.content.ofx.Comment)
			{
				renderComment((org.openfuxml.content.ofx.Comment) s);
			}
		}

		// Title after comment!
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.content.ofx.Title)
			{
				renderTitel((org.openfuxml.content.ofx.Title) s);
			}
		}
		
		//table helper for border handling...
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.content.table.Table)
			{
				tableCount++;
			}
		}
		
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.content.ofx.Paragraph)
			{
				paragraphCount++;
			}
		}
		
		// content ....
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof String){}
			else if (s instanceof org.openfuxml.content.ofx.Section)
			{
				renderSection((org.openfuxml.content.ofx.Section) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Paragraph)
			{
				paragraphCurrent++;	
				paragraphRenderer((org.openfuxml.content.ofx.Paragraph) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Highlight)
			{
				highlightRenderer((org.openfuxml.content.ofx.Highlight) s);
			}
			else if (s instanceof org.openfuxml.content.table.Table)
			{
				//table helper border handling..
				tableCurrent++;				
				renderTable((org.openfuxml.content.table.Table) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Marginalia)
			{
				renderMarginalia((org.openfuxml.content.ofx.Marginalia) s);
			}
			else if (s instanceof org.openfuxml.content.list.List)
			{
				renderList((org.openfuxml.content.list.List) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Listing)
			{
				renderListing((org.openfuxml.content.ofx.Listing) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Include)
			{
				renderInclude((org.openfuxml.content.ofx.Include) s);
			}
			else if (s instanceof org.openfuxml.content.media.Image)
			{
				renderImage((org.openfuxml.content.media.Image) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Comment)
			{}
			else if (s instanceof org.openfuxml.content.ofx.Title)
			{}
			else
			{
				logger.warn("No Renderer for Element " + s.getClass().getSimpleName());
			}
		}
		//doc.getSections().add(sectionToAdd);
	}

	private void renderComment(org.openfuxml.content.ofx.Comment s)
	{
		WordCommentRenderer wCR = new WordCommentRenderer(doc, builder);
		wCR.render(s);
	}

	private void renderTitel(org.openfuxml.content.ofx.Title s)
	{
		WordTitleRenderer wTR = new WordTitleRenderer(doc, builder);
		wTR.render(s);
	}

	private void renderImage(org.openfuxml.content.media.Image s) throws Exception
	{
		WordImageRenderer wIR = new WordImageRenderer(doc, builder);
		wIR.render(s);
	}

	//to do
	private void renderInclude(org.openfuxml.content.ofx.Include s)
	{
		logger.trace("WordSectionRenderer.renderInclude()");
	}

	//to do
	private void renderListing(org.openfuxml.content.ofx.Listing s)
	{
		logger.trace("WordSectionRenderer.renderListing()");
	}

	private void renderList(org.openfuxml.content.list.List s)
	{
		WordListRenderer wPF = new WordListRenderer(doc, builder);
		if (listNumbersFormating) {wPF.render(s,true);}
		else if (!listNumbersFormating) {wPF.render(s);}
	}

	//to do
	private void renderMarginalia(org.openfuxml.content.ofx.Marginalia s)
	{
		logger.trace("WordSectionRenderer.renderMarginalia()");
	}

	private void renderTable(org.openfuxml.content.table.Table s) throws Exception
	{
		WordTableRenderer wTR = new WordTableRenderer(doc, builder);
		wTR.render(s,tableCount,tableCurrent);
	}

	//to do
	private void highlightRenderer(org.openfuxml.content.ofx.Highlight s)
	{
		logger.trace("WordSectionRenderer.highlightRenderer()");
	}

	private void paragraphRenderer(org.openfuxml.content.ofx.Paragraph s) throws OfxAuthoringException
	{
		WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
		wPF.render(s,paragraphCount,paragraphCurrent);
	}

	private void renderSection(org.openfuxml.content.ofx.Section ofxSection) throws Exception
	{
		WordSectionRenderer sf = new WordSectionRenderer(doc,builder);
		sf.render(ofxSection);
	}

}
