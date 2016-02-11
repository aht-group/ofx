package org.openfuxml.renderer.html.head;

import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlAttribute;
import org.openfuxml.renderer.html.HtmlElement;

import java.util.ArrayList;
import java.util.List;

public class HtmlHead extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	public HtmlHead(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public void render()
	{
		List<HtmlAttribute> attr = new ArrayList<HtmlAttribute>();
		HtmlElement tag = new HtmlElement();
		attr.add(new HtmlAttribute("charset", "UTF-8"));
		txt.add(tag.openTag("head", false));
		txt.add(tag.meta(attr));
		//PageTitle ? && <link> for css ?
		txt.add(tag.closeTag("head"));
	}
}
