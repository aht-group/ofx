package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlMarginaliaRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlMarginaliaRenderer.class);
	static int count=0;

	public HtmlMarginaliaRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(HtmlElement parent, Marginalia marginalia)
	{
		parent.setAttribute("id","marginalia"+ ++count);
		parent.setAttribute("style", "background-color:lightgray");
		for(Object o : marginalia.getContent())
		{
			if(o instanceof String){parent.addContent(((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(parent, ((Emphasis)o));}
			else if(o instanceof Image){imageRenderer(parent,(Image)o);}
			else if(o instanceof Paragraph){paragraphContentRenderer(parent, ((Paragraph)o));}
		}
	}

	public void imageRenderer(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cmm, dsm);
		imgRenderer.renderInline(p, i);
		imgRenderer.marginaliaFloatStyle(p,4,4);
	}
}
