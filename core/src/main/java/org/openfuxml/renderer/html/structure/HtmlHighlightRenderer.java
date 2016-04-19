package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlHighlightRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlHighlightRenderer.class);

	@Deprecated
	public HtmlHighlightRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public HtmlHighlightRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Highlight highlight)
	{
		HtmlElement ele = new HtmlElement("div");

		ele.setAttribute("style", "background-color:lightgray;");
		for(Object o : highlight.getContent())
		{
			if(o instanceof String){ele.addContent(((String)o));}
			else if(o instanceof Marginalia){marginaliaRenderer(ele, ((Marginalia)o));}
			else if(o instanceof Paragraph){paragraphRenderer(ele, ((Paragraph)o));}
		}
		parent.addContent(ele);
	}
}
