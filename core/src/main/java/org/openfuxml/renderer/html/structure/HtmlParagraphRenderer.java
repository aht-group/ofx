package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlParagraphRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlParagraphRenderer.class);

	public HtmlParagraphRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}


	public void render(HtmlElement parent, Paragraph paragraph)
	{
		HtmlElement p = new HtmlElement("p");
		renderContent(p, paragraph);
		parent.addContent(p);
	}

	public void renderContent(HtmlElement parent, Paragraph paragraph)
	{
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){parent.addContent(((String)o));}
			else if(o instanceof Image){imageRenderer(parent,(Image)o);}
			else if(o instanceof Emphasis){renderEmphasis(parent, ((Emphasis)o));}
			else if(o instanceof Reference){renderReference(parent, ((Reference)o));}
			else if(o instanceof Marginalia){marginaliaRenderer(parent, ((Marginalia)o));}
		}
	}

	private void renderReference(HtmlElement parent, Reference r)
	{
		HtmlReferenceRenderer referenceRender = new HtmlReferenceRenderer(cmm, dsm);

		//"xxx" Sequenz ist nur ein vorläufiger Füller für den Text eines internen Links.
		referenceRender.renderIntern(parent,r,"xxx");
	}

	public void imageRenderer(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.renderInline(p, i);
	}
}
