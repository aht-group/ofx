package org.openfuxml.renderer.html.media;

import org.openfuxml.content.layout.Height;
import org.openfuxml.content.media.Image;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.xml.renderer.cmp.Html;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlImageRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlImageRenderer.class);

	public HtmlImageRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	/*Simples Image Element. Geeignet zum einen für Grafiken in den Text einzufügen.
	 * Außerhalb von Fließtext ist die @Link render() Methode zu verwenden.*/
	public void renderInline(HtmlElement parent, Image image)
	{
		String title = TxtTitleFactory.build(image.getTitle());
		HtmlElement img = new HtmlElement("img");

		img.setAttribute("alt", title);
		img.setAttribute("src", image.getMedia().getSrc());

		parent.addContent(img);
	}

	/*Figure zum Einfügen von Bildern/Grafiken unabhängig vom Fließtext.*/
	public void render(HtmlElement parent, Image image)
	{
		String title = TxtTitleFactory.build(image.getTitle());
		HtmlElement figure = new HtmlElement("figure");
		HtmlElement img = new HtmlElement("img");
		img.setAttribute("alt", title);
		img.setAttribute("src", image.getMedia().getSrc());
		if(image.isSetHeight()|| image.isSetWidth()){img.setAttribute("style", HtmlElement.evaluateSize(image.getWidth()) + HtmlElement.evaluateSize(image.getHeight()));}

		figure.addContent(img);
		figure.addContent(caption(title));
		parent.addContent(figure);
	}

	/*Fügt dem Bild/ der Grafik einen Untertitel hinzu.
	!Nur in Verbindung mit einem Figure Element!*/
	private HtmlElement caption(String title){return new HtmlElement("figcaption").addContent(title);}
}