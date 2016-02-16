package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.html.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlAttribute;
import org.openfuxml.renderer.html.HtmlElement;

import java.util.ArrayList;
import java.util.List;

public class HtmlBody extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	public HtmlBody(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public void render(Section section)
	{
		HtmlElement body = new HtmlElement("body");
		renderSection(body, section, 1);
		html.getContent().add(body);
	}

	private void renderSection(HtmlElement parent, Section section, int lvl)
	{
		HtmlSectionRenderer sectionRenderer = new HtmlSectionRenderer(cmm, dsm, lvl);
		sectionRenderer.render(parent, section);
//		for(String s : sectionRenderer.getContent()){txt.add(s);}
	}
}
