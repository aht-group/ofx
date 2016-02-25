package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.*;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class HtmlSectionRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlSectionRenderer.class);

	int lvl;

	public HtmlSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl)
	{
		super(cmm, dsm);
		this.lvl = lvl;
	}

	public void render(HtmlElement parent, Section section)
	{
		HtmlElement div = new HtmlElement("section");

		//Comments always on top!
		/*Alle Kommentare werden Html konform an den Anfang gesetz*/
		for(Object s : section.getContent())
		{
			if(s instanceof Comment){renderComment(div, (Comment)s);}
		}

		/*Der eigentliche Inhalt des Abschnittes wird hier nach Typ unterschieden
		* und verarbeitet.*/
		for(Object o : section.getContent())
		{
			if(o instanceof Section){renderSection(div,(Section)o);}
			else if(o instanceof Sections){processSections(div, ((Sections)o));}
			else if(o instanceof Title){titleRenderer(div, ((Title)o));}
//			else if(o instanceof String){}
			else if(o instanceof List){listRenderer(div,(List)o);}
			else if(o instanceof Paragraph){paragraphRenderer(div,(Paragraph)o);}
			else if(o instanceof Image){imageRenderer(div,(Image)o);}
			else if(o instanceof Marginalia){marginaliaRenderer(div,(Marginalia)o);}
			else if(o instanceof Highlight){highlightRenderer(div,(Highlight)o);}
			else if(o instanceof Table){renderTable(div,(Table)o);}
			else if(o instanceof Listing){renderListing(div,(Listing)o);}
		}
		parent.addContent(div);
	}

	private void renderListing(HtmlElement div, Listing listing)
	{
		HtmlListingRenderer listingRenderer = new HtmlListingRenderer(cmm, dsm);
		listingRenderer.render(div, listing);
	}

	private void highlightRenderer(HtmlElement div, Highlight highlight)
	{
		HtmlHighlightRenderer Hrenderer = new HtmlHighlightRenderer(cmm,dsm);
		Hrenderer.render(div, highlight);
	}

	/*
	Verarbeitet sogenannte Subsection
	@para section: Objekt der untergeordneten Section
	*/
	private void renderSection(HtmlElement parent, Section section)
	{
		HtmlSectionRenderer sr = new HtmlSectionRenderer(cmm,dsm,++lvl);
		sr.render(parent, section);
	}

	/*Verarbeitet Kommentare
	* @para comment: Kommentar Objekt*/
	private void renderComment(HtmlElement parent, Comment comment)
	{
		HtmlCommentRenderer commentR = new HtmlCommentRenderer(cmm, dsm);
		commentR.render(parent, comment);
	}

	private void titleRenderer(HtmlElement parent, Title title)
	{
		HtmlHeadingRenderer titleR = new HtmlHeadingRenderer(cmm, dsm);
		/*@para lvl: Gibt die Hierarchie-Ebene der Überschrift an.*/
		titleR.render(parent, title,lvl);
	}

	private void processSections(HtmlElement div, Sections sections)
	{
		for(Object o : sections.getContent())
		{
			if(o instanceof Section){renderSection(div,(Section)o);}
		}
	}
}
