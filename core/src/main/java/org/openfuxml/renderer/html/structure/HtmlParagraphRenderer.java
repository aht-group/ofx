package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
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
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){p.addContent(((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(p, ((Emphasis)o));}
			else if(o instanceof Image){renderImage(p,(Image)o);}
		}
		parent.addContent(p);
	}

	private void renderEmphasis(HtmlElement p, Emphasis em)
	{
		HtmlEmphasisRenderer emph = new HtmlEmphasisRenderer(cmm,dsm);
		emph.render(p, em);
	}

	private void renderImage(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.renderInline(p, i);
	}
}
