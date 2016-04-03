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
	private HtmlElement body;

	public HtmlBody(ConfigurationProvider cp) {
		super(cp);
	}

	/*Verarbeitet die oberste Ebene eines HTML Dokumentes.
	Fürgt das erzeugte body Objekt dem html Objekt aus der Klasse HtmlDocumentRenderer hinzu.
	@param content: Wurzelobjekt des Dokumentes.
	 */
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
		HtmlDocumentRenderer.getInstance().getHtml().getContent().add(body);
	}

	/*Verarbeitet ein Section Objekt als Wurzelobjekt. Hautsächlich für Tests.
	Fürgt das erzeugte body Objekt dem html Objekt aus der Klasse HtmlDocumentRenderer hinzu.
	@param section: Wurzelobjekt des Tests.
	 */
	public void render(Section section)
	{
		new HtmlDocumentRenderer(cp,"").init();
		if(HtmlDocumentRenderer.getInstance().getHtml().getChild("body") == null)
		{
			body = new HtmlElement("body");
			HtmlDocumentRenderer.getInstance().getHtml().getContent().add(body);
		}
		else{body.removeContent();}
		renderSection(body, section, 1);
	}

	/*

	 */
	private void resetCounter()
	{
		HtmlImageRenderer.imgcount = 0;
		HtmlTableRenderer.tblcount = 0;
	}
}
