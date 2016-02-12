package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
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
		//Comments always on top!
		/*Alle Kommentare werden Html konform an den Anfang gesetz*/
		for(Object s : section.getContent())
		{
			if(s instanceof Comment){renderComment((Comment)s);}
		}

		/*@para lvl: Gibt die Hierarchie-Ebene der Überschrift an.*/
		for(Object s : section.getContent())
		{
			if(s instanceof Title)
			{
				HtmlHeadingRenderer titleR = new HtmlHeadingRenderer(cmm, dsm);
				titleR.render(parent, (Title)s,lvl);
			}
		}

		/*Der eigentliche Inhalt des Abschnittes wird hier nach Typ unterschieden
		* und verarbeitet.*/
		for(Object o : section.getContent())
		{
			if(o instanceof Section){renderSection(parent,(Section)o);}
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
			else if(o instanceof Paragraph){paragraphRenderer(parent,(Paragraph)o);}
//			else if(o instanceof Image){imageRenderer((Image)o);}
		}
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
	private void renderComment(Comment comment)
	{
//		MdCommentRenderer commentR = new MdCommentRenderer(cmm, dsm);
//		commentR.render(comment);
	}
}
