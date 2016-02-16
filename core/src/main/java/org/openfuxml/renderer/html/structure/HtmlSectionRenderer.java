package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.*;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		HtmlElement div = new HtmlElement("div");

		//Comments always on top!
		/*Alle Kommentare werden Html konform an den Anfang gesetz*/
		for(Object s : section.getContent())
		{
			if(s instanceof Comment){renderComment(div, (Comment)s);}
		}

		/*@para lvl: Gibt die Hierarchie-Ebene der Überschrift an.*/
		for(Object s : section.getContent())
		{
			if(s instanceof Title)
			{
				HtmlHeadingRenderer titleR = new HtmlHeadingRenderer(cmm, dsm);
				titleR.render(div, (Title)s,lvl);
			}
		}

		/*Der eigentliche Inhalt des Abschnittes wird hier nach Typ unterschieden
		* und verarbeitet.*/
		for(Object o : section.getContent())
		{
			if(o instanceof Section){renderSection(div,(Section)o);}
//			else if(o instanceof String){}
//			else if(o instanceof List)
//			{
//				if(((List)o).isSetComment())
//				{
//					MdCommentRenderer.first = true;
//					renderComment(((List)o).getComment());
//				}
//				listRenderer((List)o);
//			}
			else if(o instanceof Paragraph){paragraphRenderer(div,(Paragraph)o);}
			else if(o instanceof Image){imageRenderer(div,(Image)o);}
			else if(o instanceof Marginalia){marginaliaRenderer(div,(Marginalia)o);}
		}
		parent.addContent(div);
	}

	/*
	Verarbeitet sogenannte Subsection
	@para section: Objekt der untergeordneten Section
	*/
	private void renderSection(HtmlElement parent, Section section)
	{
		HtmlSectionRenderer sr = new HtmlSectionRenderer(cmm,dsm,lvl+1);
		sr.render(parent, section);
	}

	/*Verarbeitet Kommentare
	* @para comment: Kommentar Objekt*/
	private void renderComment(HtmlElement parent, Comment comment)
	{
		HtmlCommentRenderer commentR = new HtmlCommentRenderer(cmm, dsm);
		commentR.render(parent, comment);
	}
}
