package org.openfuxml.renderer.docx.aspose.structure;

import org.apache.commons.lang3.StringUtils;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.docx.aspose.content.AsposeDocxParagraphRenderer;
import org.openfuxml.renderer.docx.aspose.content.AsposeDocxTitleRenderer;
import org.openfuxml.renderer.docx.aspose.util.OfxAsposeDocxFont;
import org.openfuxml.renderer.docx.aspose.util.OfxAsposeDocxParagraph;
import org.openfuxml.renderer.word.content.WordCommentRenderer;
import org.openfuxml.renderer.word.content.WordImageRenderer;
import org.openfuxml.renderer.word.content.WordListRenderer;
import org.openfuxml.renderer.word.content.WordTableRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;

public class AsposeDocxSectionRenderer
{
	final static Logger logger = LoggerFactory.getLogger(AsposeDocxSectionRenderer.class);

	private final Document doc;
	private final DocumentBuilder builder;
	
	private int tableCount=0;
	private int tableCurrent=0;
	private int paragraphCount=0;
	private boolean listNumbersFormating=false;
	private int paragraphCurrent;

	public AsposeDocxSectionRenderer(Document doc, DocumentBuilder builder)
	{
		this.doc=doc;
		this.builder=builder;
	}
	
	public void render(org.openfuxml.model.xml.core.ofx.Section ofxSection, boolean listNumbersFormating)
	{
		this.listNumbersFormating=listNumbersFormating;
		try {renderWithException(ofxSection);} catch (Exception e) {}
	}
	
	public void render(org.openfuxml.model.xml.core.ofx.Section ofxSection, String fallbackContent)
	{
		if(ofxSection==null) {builder.write(fallbackContent);}
		else {render(ofxSection);}
	}
	
	public void render(org.openfuxml.model.xml.core.ofx.Section ofxSection)
	{
		if(ofxSection!=null)
		{
			try
			{
				renderWithException(ofxSection);
			}
			catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public void renderWithException(org.openfuxml.model.xml.core.ofx.Section ofxSection) throws Exception
	{
		//Section sectionToAdd = new Section(doc);

		// Comment always on top!
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.model.xml.core.ofx.Comment)
			{
				renderComment((org.openfuxml.model.xml.core.ofx.Comment) s);
			}
		}

		// Title after comment!
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.model.xml.core.ofx.Title)
			{
				renderTitel((org.openfuxml.model.xml.core.ofx.Title) s);
			}
		}
		
		//table helper for border handling...
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.model.xml.core.table.Table)
			{
				tableCount++;
			}
		}
		
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof org.openfuxml.model.xml.core.ofx.Paragraph)
			{
				paragraphCount++;
			}
		}
		
		// content ....
		for (Object s : ofxSection.getContent())
		{
			if (s instanceof String){}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Section)
			{
				renderSection((org.openfuxml.model.xml.core.ofx.Section) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Paragraph)
			{
				paragraphCurrent++;	
				paragraphRenderer((org.openfuxml.model.xml.core.ofx.Paragraph) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Highlight)
			{
				highlightRenderer((org.openfuxml.model.xml.core.ofx.Highlight) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.table.Table)
			{
				//table helper border handling..
				tableCurrent++;				
				renderTable((org.openfuxml.model.xml.core.table.Table) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Marginalia)
			{
				renderMarginalia((org.openfuxml.model.xml.core.ofx.Marginalia) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.list.List)
			{
				renderList((org.openfuxml.model.xml.core.list.List) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Listing)
			{
				renderListing((org.openfuxml.model.xml.core.ofx.Listing) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Include)
			{
				renderInclude((org.openfuxml.model.xml.core.ofx.Include) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.media.Image)
			{
				renderImage((org.openfuxml.model.xml.core.media.Image) s);
			}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Comment)
			{}
			else if (s instanceof org.openfuxml.model.xml.core.ofx.Title)
			{}
			else
			{
				logger.warn("No Renderer for Element " + s.getClass().getSimpleName());
			}
		}
		//doc.getSections().add(sectionToAdd);
	}

	private void renderComment(org.openfuxml.model.xml.core.ofx.Comment s)
	{
		WordCommentRenderer wCR = new WordCommentRenderer(doc, builder);
		wCR.render(s);
	}

	private void renderTitel(org.openfuxml.model.xml.core.ofx.Title s)
	{
		AsposeDocxTitleRenderer wTR = new AsposeDocxTitleRenderer(builder);
		wTR.render(s);
	}

	private void renderImage(org.openfuxml.model.xml.core.media.Image s) throws Exception
	{
		WordImageRenderer wIR = new WordImageRenderer(builder);
		wIR.render(s);
	}

	//to do
	private void renderInclude(org.openfuxml.model.xml.core.ofx.Include s)
	{
		logger.trace("WordSectionRenderer.renderInclude()");
	}

	//to do
	private void renderListing(org.openfuxml.model.xml.core.ofx.Listing s)
	{
		logger.trace("WordSectionRenderer.renderListing()");
	}

	private void renderList(org.openfuxml.model.xml.core.list.List s)
	{
		WordListRenderer wPF = new WordListRenderer(doc, builder);
		if (listNumbersFormating) {wPF.render(s,true);}
		else if (!listNumbersFormating) {wPF.render(s);}
	}

	//to do
	private void renderMarginalia(org.openfuxml.model.xml.core.ofx.Marginalia s)
	{
		logger.trace("WordSectionRenderer.renderMarginalia()");
	}

	private void renderTable(org.openfuxml.model.xml.core.table.Table s) throws Exception
	{
		WordTableRenderer wTR = new WordTableRenderer(builder);
		wTR.render(s,tableCount,tableCurrent);
	}

	//to do
	private void highlightRenderer(org.openfuxml.model.xml.core.ofx.Highlight s)
	{
		logger.trace("WordSectionRenderer.highlightRenderer()");
	}

	private void paragraphRenderer(org.openfuxml.model.xml.core.ofx.Paragraph s) throws OfxAuthoringException
	{
		AsposeDocxParagraphRenderer wPF = new AsposeDocxParagraphRenderer(builder);
		wPF.render(s,paragraphCount,paragraphCurrent);
	}

	private void renderSection(org.openfuxml.model.xml.core.ofx.Section ofxSection) throws Exception
	{
		AsposeDocxSectionRenderer sf = new AsposeDocxSectionRenderer(doc,builder);
		sf.renderWithException(ofxSection);
	}
	
	public AsposeDocxSectionRenderer font(OfxAsposeDocxFont font) {font.apply(builder); return this;}
	public AsposeDocxSectionRenderer paragraph(OfxAsposeDocxParagraph paragraph) {paragraph.apply(builder); return this;}
	
	public AsposeDocxSectionRenderer cell() {builder.insertCell(); return this;}
	public void write(String text) {builder.write(text);}
	public void writeln(String text) {builder.writeln(text);}
	
	public AsposeDocxSectionRenderer tabs(int value) {builder.write(StringUtils.repeat("\t",value)); return this;}
}