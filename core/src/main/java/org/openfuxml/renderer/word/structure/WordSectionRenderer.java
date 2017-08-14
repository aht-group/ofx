package org.openfuxml.renderer.word.structure;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Section;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.*;

public class WordSectionRenderer
{
	final static Logger logger = LoggerFactory.getLogger(WordSectionRenderer.class);

	Document doc;
	DocumentBuilder builder;

	public WordSectionRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc = doc;
		this.builder = builder;
	}

	public void render(org.openfuxml.content.ofx.Section ofxSection) throws OfxAuthoringException, OfxConfigurationException
	{
		logger.trace("WordSectionRenderer.render()");
		
		Section sectionToAdd = new Section(doc);

		// Comment always on top!
		for (Object o : ofxSection.getContent())
		{
			if (o instanceof org.openfuxml.content.ofx.Comment)
			{
				renderComment((org.openfuxml.content.ofx.Comment) o);
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

		for (Object s : ofxSection.getContent())
		{

			if (s instanceof String)
			{

			}
			else if (s instanceof org.openfuxml.content.ofx.Section)
			{
				renderSection((org.openfuxml.content.ofx.Section) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Paragraph)
			{
				paragraphRenderer((org.openfuxml.content.ofx.Paragraph) s, true);
			}
			else if (s instanceof org.openfuxml.content.ofx.Highlight)
			{
				highlightRenderer((org.openfuxml.content.ofx.Highlight) s);
			}
			else if (s instanceof org.openfuxml.content.table.Table)
			{
				renderTable((org.openfuxml.content.table.Table) s);
			}
			else if (s instanceof org.openfuxml.content.ofx.Marginalia)
			{
				renderMarginalia((org.openfuxml.content.ofx.Marginalia) s);
			}
			else if (s instanceof org.openfuxml.content.list.List)
			{
				renderList((org.openfuxml.content.list.List) s, this);
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
		// doc.getSections().add(sectionToAdd);
	}

	private void renderComment(org.openfuxml.content.ofx.Comment o)
	{
		logger.trace("WordSectionRenderer.renderComment()");
		WordCommentRenderer wCR = new WordCommentRenderer(doc, builder);
		wCR.render(o);
	}

	private void renderTitel(org.openfuxml.content.ofx.Title s)
	{
		// TODO Auto-generated method stub

	}

	private void renderImage(org.openfuxml.content.media.Image s)
	{
		logger.trace("WordSectionRenderer.renderImage()");

	}

	private void renderInclude(org.openfuxml.content.ofx.Include s)
	{
		logger.trace("WordSectionRenderer.renderInclude()");
	}

	private void renderListing(org.openfuxml.content.ofx.Listing s)
	{
		logger.trace("WordSectionRenderer.renderListing()");
	}

	private void renderList(org.openfuxml.content.list.List s, WordSectionRenderer wordSectionRenderer)
	{
		logger.trace("WordSectionRenderer.renderList()");

	}

	private void renderMarginalia(org.openfuxml.content.ofx.Marginalia s)
	{
		logger.trace("WordSectionRenderer.renderMarginalia()");
	}

	private void renderTable(org.openfuxml.content.table.Table s)
	{
		logger.trace("WordSectionRenderer.renderTable()");
	}

	private void highlightRenderer(org.openfuxml.content.ofx.Highlight s)
	{
		logger.trace("WordSectionRenderer.highlightRenderer()");
	}

	private void paragraphRenderer(org.openfuxml.content.ofx.Paragraph s, boolean b)
	{
		logger.trace("WordSectionRenderer.paragraphRenderer()");
		WordParagraphRenderer wPF = new WordParagraphRenderer(doc, builder);
		wPF.render(s);
	}

	private void renderSection(org.openfuxml.content.ofx.Section ofxSection) throws OfxAuthoringException, OfxConfigurationException
	{
		logger.trace("WordSectionRenderer.renderSection()");
		WordSectionRenderer sf = new WordSectionRenderer(doc,builder);
		sf.render(ofxSection);
	}

}
