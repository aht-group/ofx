package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Rebecca Roblick
 */
public class HtmlEmphasisRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlEmphasisRenderer.class);
	boolean bold=false,
			italic=false,
			quote=false,
			typewriter=false,
			underline=false;

	public HtmlEmphasisRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}

	/*Verarbeitet die Klassische Textgestaltung wie Fettdruck etc.*/
	public void render(HtmlElement parent, Emphasis emphasis)
	{
		int countAttr = 0;

		if(emphasis.isSetBold()){bold = emphasis.isBold(); if(bold){countAttr++;}}
		if(emphasis.isSetItalic()){italic = emphasis.isItalic(); if(italic){countAttr++;}}
		if(emphasis.isSetQuote()){quote = emphasis.isQuote(); if(quote){countAttr++;}}
		if(emphasis.isSetUnderline()){underline = emphasis.isUnderline(); if(underline){countAttr++;}}
		if(emphasis.isSetStyle()){typewriter = emphasis.getStyle().equals("typewriter");if(typewriter){countAttr++;}}

		if(countAttr == 1){parent.addContent(simpleEmphasis(emphasis.getValue()));}
		else{complexEmphasis(parent,emphasis.getValue(),countAttr);}
	}

	private HtmlElement simpleEmphasis(String value)
	{
		if(bold){bold=false; return new HtmlElement("b").addContent(value);}
		if(italic){italic=false; return new HtmlElement("i").addContent(value);}
		if(typewriter){typewriter=false; HtmlElement emph = new HtmlElement("span").addContent(value); emph.setStyleAttribute("font-family:'Courier New',Courier,monospace"); return emph;}
		if(quote){quote=false; return new HtmlElement("q").addContent(value);}
		if(underline){underline=false; return new HtmlElement("ins").addContent(value);}
		return null;
	}

	private void complexEmphasis(HtmlElement parent, String value, int count)
	{
		HtmlElement mom = simpleEmphasis(null);
		count--;
		HtmlElement temp = mom;
		for(;count > 0; count--)
		{
			HtmlElement temp2 = simpleEmphasis(null);
			if(temp2 != null && temp != null)
			{
				temp.addContent(temp2);
				temp = temp2;
				if(count == 1){temp2.addContent(value);}
			}
		}
		parent.addContent(mom);
	}
}
