package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlEmphasisRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlEmphasisRenderer.class);

	public HtmlEmphasisRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public void render(HtmlElement parent, Emphasis emphasis)
	{
		boolean bold = emphasis.isSetBold() && emphasis.isBold();
		boolean italic = emphasis.isSetItalic() && emphasis.isItalic();
		boolean typewriter = emphasis.isSetStyle() && emphasis.getStyle().equals("typewriter");
		boolean quote = emphasis.isSetQuote() && emphasis.isQuote();

		HtmlElement emph = null;
		if(bold){emph = new HtmlElement("bold");}
		if(italic){emph = new HtmlElement("italic");}
		if(typewriter){emph = new HtmlElement("code");}
		if(quote){emph = new HtmlElement("q");}
		if(bold && italic)
		{
			emph = new HtmlElement("bold"); emph.addContent(new HtmlElement("italic"));
			emph.getChild("italic").addContent(emphasis.getValue());
		}
		if(emph != null && emph.getContent().size() == 0){emph.addContent(emphasis.getValue());}
		parent.addContent(emph);
	}
}
