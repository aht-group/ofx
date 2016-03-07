package org.openfuxml.renderer.html.media;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.media.Image;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlImageRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlImageRenderer.class);

	public static int imgcount = 0;

	@Deprecated
	public HtmlImageRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlImageRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	/*Simples Image Element. Geeignet zum einen für Grafiken in den Text einzufügen.
		 * Außerhalb von Fließtext ist die @Link render() Methode zu verwenden.*/
	public void renderInline(HtmlElement parent, Image image)
	{
		HtmlElement img = new HtmlElement("img");
		String title = "This is an image.";
		if(image.getTitle() != null){title = TxtTitleFactory.build(image.getTitle());}
		img.setAttribute("alt", title);

		img.setAttribute("src", image.getMedia().getSrc());

		parent.addContent(img);
	}

	/*Figure zum Einfügen von Bildern/Grafiken unabhängig vom Fließtext.*/
	public void render(HtmlElement parent, Image image)
	{
		String title = "This is an image.";
		if(image.getTitle() != null){title = TxtTitleFactory.build(image.getTitle());}
		imgcount++;
		HtmlElement figure = new HtmlElement("figure");
		HtmlElement img = new HtmlElement("img");
		img.setAttribute("id", image.getId());
		img.setAttribute("alt", title);
		img.setAttribute("src", image.getMedia().getSrc());
		if(image.isSetHeight()|| image.isSetWidth()){img.setStyleAttribute(HtmlElement.evaluateSize(image.getWidth()) + HtmlElement.evaluateSize(image.getHeight()));}

		figure.addContent(img);
		figure.addContent(caption(title));
		parent.addContent(figure);
	}

	/*Fügt dem Bild/der Grafik einen Untertitel hinzu.
	!Nur in Verbindung mit einem Figure Element!*/
	private HtmlElement caption(String title){return new HtmlElement("figcaption").addContent(title);}

	public void marginaliaFloatStyle(HtmlElement parent, String size)
	{
		((HtmlElement)parent.getChild("img")).setStyleAttribute("float: left;" + size);
	}
}
