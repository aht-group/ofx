package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;


public class HtmlBody extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	private static HtmlElement body;

	@Deprecated
	public HtmlBody(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public HtmlBody(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(Content content)
	{
		body = new HtmlElement("body");
		HtmlElement cont = new HtmlElement("div");
		for(Object c : content.getContent())
		{
			if(c instanceof Sections){
				for(Object s : ((Sections)c).getContent())
				{
					if(s instanceof Section){renderSection(cont, ((Section)s), 1);}
				}
			}
			else if(c instanceof Section){renderSection(cont, ((Section)c), 1);}
		}
		body.addContent(cont);
		html.getContent().add(body);
	}

	public void render(Section section)
	{
		if(html.getChild("body") == null)
		{
			body = new HtmlElement("body");
			html.getContent().add(body);
		}
		else{body.removeContent();}
		renderSection(body, section, 1);
	}

	private void renderSection(HtmlElement parent, Section section, int lvl)
	{
		HtmlSectionRenderer sectionRenderer = new HtmlSectionRenderer(cp, lvl);
		sectionRenderer.render(parent, section);
	}
}
