package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.OfxHTMLRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.renderer.markdown.structure.MdCommentRenderer;
import org.openfuxml.renderer.markdown.structure.MdTitleRenderer;
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

	public void render(Section section)
	{
		//Comments always on top!
		/*Kommentare immer zuerst*/
		for(Object s : section.getContent())
		{
			if(s instanceof Comment){renderComment((Comment)s);}
		}

		//Title always immediately after comments
		/*Überschriften direkt nach den Kommentaren*/
		for(Object s : section.getContent())
		{
			if(s instanceof Title)
			{
				HtmlTitleRenderer titleR = new HtmlTitleRenderer(cmm, dsm);
				titleR.render((Title)s,lvl);
				renderer.add(titleR);
			}
		}
//		for(Object o : section.getContent())
//		{
//			if(o instanceof Section){renderSection((Section)o);}
//			else if(o instanceof String){txt.add(((String)o).trim());}
//			else if(o instanceof List)
//			{
//				if(((List)o).isSetComment())
//				{
//					MdCommentRenderer.first = true;
//					renderComment(((List)o).getComment());
//				}
//				listRenderer((List)o);
//			}
//			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o);}
//			else if(o instanceof Image){imageRenderer((Image)o);}
//		}
	}

	/*
	Verarbeitet sogenannte Subsection
	@para section: Objekt einer neuen Section
	*/
	private void renderSection(Section section)
	{
		HtmlSectionRenderer sr = new HtmlSectionRenderer(cmm,dsm,lvl+1);
		sr.render(section);
		renderer.add(sr);
	}


	private void renderComment(Comment comment)
	{
//		MdCommentRenderer commentR = new MdCommentRenderer(cmm, dsm);
//		commentR.render(comment);
//		renderer.add(commentR);
	}
}
