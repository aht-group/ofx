package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.openfuxml.renderer.html.table.HtmlTableRenderer;


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
			resetCounter();
			if(c instanceof Sections){
				for(Object s : ((Sections)c).getContent())
				{
					if(s instanceof Section){renderSection(cont, ((Section)s), 1);}
				}
			}
			else if(c instanceof Section){renderSection(cont, ((Section)c), 1);}
		}
		body.addContent(cont);
		HtmlDocumentRenderer.html.getContent().add(body);
	}

	public void render(Section section)
	{

		new HtmlDocumentRenderer(cp,"").init();
		if(HtmlDocumentRenderer.html.getChild("body") == null)
		{
			body = new HtmlElement("body");
			HtmlDocumentRenderer.html.getContent().add(body);
		}
		else{body.removeContent();}
		renderSection(body, section, 1);
	}

	private void resetCounter()
	{
		HtmlImageRenderer.imgcount = 0;
		HtmlTableRenderer.tblcount = 0;
	}

	private void renderSection(HtmlElement parent, Section section, int lvl)
	{
		HtmlSectionRenderer sectionRenderer = new HtmlSectionRenderer(cp, lvl);
		sectionRenderer.render(parent, section);
	}
}
