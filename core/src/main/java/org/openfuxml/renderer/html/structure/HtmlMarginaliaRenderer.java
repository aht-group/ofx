package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlMarginaliaRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlMarginaliaRenderer.class);
	static int count=0;

	@Deprecated
	public HtmlMarginaliaRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}

	public HtmlMarginaliaRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Marginalia marginalia)
	{
		parent.setStyleAttribute("display:table-row;");
		HtmlElement div = new HtmlElement("div");
		div.setStyleAttribute("float: left;width:15%;margin-right: 5px;");
		for(Object o : marginalia.getContent())
		{
			if(o instanceof String){div.addContent(((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(div, ((Emphasis)o));}
			else if(o instanceof Image){imageRenderer(div,(Image)o);}
			else if(o instanceof Paragraph){paragraphContentRenderer(div, ((Paragraph)o));}
		}
		parent.addContent(div);
	}

	public void imageRenderer(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cp);
		imgRenderer.renderInline(p, i);
		if(i.isSetHeight() || i.isSetWidth())
			imgRenderer.marginaliaFloatStyle(p,HtmlElement.evaluateSize(i));
		else
			imgRenderer.marginaliaFloatStyle(p,"width:4em;height:4em;");
	}
}
