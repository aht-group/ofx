package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.content.text.Symbol;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.openfuxml.renderer.html.util.HtmlFontUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlParagraphRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlParagraphRenderer.class);

	@Deprecated
	public HtmlParagraphRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlParagraphRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Paragraph paragraph)
	{
		HtmlElement p = new HtmlElement("p");
		renderContent(p,parent, paragraph);
		if(p.getParent() == null)
			parent.addContent(p);
	}

	public void renderWithout (HtmlElement parent, Paragraph paragraph)
	{
		renderContent(parent,null, paragraph);
	}

	public void renderContent(HtmlElement parent,HtmlElement grandparent, Paragraph paragraph)
	{
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){parent.addContent(((String)o));}
			else if(o instanceof Image){imageRenderer(parent,(Image)o);}
			else if(o instanceof Emphasis){renderEmphasis(parent, ((Emphasis)o));}
			else if(o instanceof Reference){renderReference(parent, ((Reference)o));}
			else if(o instanceof Symbol){renderSymbol(parent, ((Symbol)o));}
			else if(o instanceof Font){parent.setStyleAttribute(HtmlFontUtil.fontSize(((Font)o)));}
			else if(o instanceof Marginalia)
			{
				HtmlElement div = new HtmlElement("div");
				marginaliaRenderer(div, ((Marginalia)o));
				parent.setStyleAttribute("margin:0;");
				div.addContent(parent);
				grandparent.addContent(div);
			}
		}
	}

	private void renderReference(HtmlElement parent, Reference r)
	{
		HtmlReferenceRenderer referenceRender = new HtmlReferenceRenderer(cp);
		if(r.isSetType() && r.getType().equals("external"))
				referenceRender.renderExtern(parent,r.getTarget(), r.getValue());
		else
			referenceRender.renderIntern(parent,r);
	}

	public void imageRenderer(HtmlElement p, Image i)
	{
		HtmlImageRenderer imgRenderer = new HtmlImageRenderer(cp);
		imgRenderer.renderInline(p, i);
	}
}
