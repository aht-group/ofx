package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.*;
import org.openfuxml.content.table.Table;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class HtmlSectionRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlSectionRenderer.class);

	int lvl;
	static int sectionCount = 0;

	@Deprecated
	public HtmlSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl)
	{
		super(cmm, dsm);
		this.lvl = lvl;
	}

	public HtmlSectionRenderer(ConfigurationProvider cp, int lvl) {
		super(cp);
		this.lvl = lvl;
	}

	/*
	<section> tag als HtmlElement Objekt erzeugen und
	Kommentare zuerst umwandeln, an den Anfang setzten,
	danach den eigentlich Inhalt abarbeiten
	*/
	public void render(HtmlElement parent, Section section)
	{
		if (lvl == 1) {sectionCount++;}

		HtmlElement div = new HtmlElement("section");

		//Comments always on top!

		for(Object s : section.getContent())
		{
			if(s instanceof Comment){commentRenderer(div, (Comment)s);}
		}

		/*Der eigentliche Inhalt des Abschnittes wird hier nach Typ unterschieden
		* und verarbeitet.*/
		for(Object o : section.getContent())
		{
			if(o instanceof Section){renderSection(div,(Section)o, ++lvl);}
			else if(o instanceof Sections){processSections(div, ((Sections)o));}
			else if(o instanceof Title){titleRenderer(div, ((Title)o));}
			else if(o instanceof String){div.addContent(((String) o));}
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
/*HtmlListingRenderer aufrufen um Listing Objekte zu verarbeiten*/
	private void renderListing(HtmlElement div, Listing listing)
	{
		HtmlListingRenderer listingRenderer = new HtmlListingRenderer(cp);
		listingRenderer.render(div, listing);
	}

	/*HtmlHighlightRenderer aufrufen um Highlight Objekte zu verarbeiten*/
	private void highlightRenderer(HtmlElement div, Highlight highlight)
	{
		HtmlHighlightRenderer Hrenderer = new HtmlHighlightRenderer(cp);
		Hrenderer.render(div, highlight);
	}

//	/*
//	Verarbeitet sogenannte Subsection
//	@para section: Objekt der untergeordneten Section
//	*/
//	private void renderSection(HtmlElement parent, Section section)
//	{
//		HtmlSectionRenderer sr = new HtmlSectionRenderer(cp,++lvl);
//		sr.render(parent, section);
//	}

	/*HtmlHeadingRenderer aufrufen um Title Objekte zu verarbeiten*/
	private void titleRenderer(HtmlElement parent, Title title)
	{
		HtmlHeadingRenderer titleR = new HtmlHeadingRenderer(cp);
		/*@para lvl: Gibt die Hierarchie-Ebene der Überschrift an.*/
		titleR.render(parent, title,lvl);
	}

	/*Sections Objekte nach weiteren Section Objekten durchsuchen und diese verarbeiten*/
	private void processSections(HtmlElement div, Sections sections)
	{
		for(Object o : sections.getContent())
		{
			if(o instanceof Section){renderSection(div,(Section)o,++lvl);}
		}
	}
}
