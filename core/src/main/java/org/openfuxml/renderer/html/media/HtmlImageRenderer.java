package org.openfuxml.renderer.html.media;
import java.util.Objects;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.media.Image;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.structure.HtmlSectionRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlImageRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlImageRenderer.class);

	public static int imgcount = 0;

	public HtmlImageRenderer(ConfigurationProvider cp)
	{
		super(cp);
		if(Objects.isNull(cp.getCrossMediaManager())) {logger.warn(ConfigurationProvider.class.getSimpleName()+"."+CrossMediaManager.class.getSimpleName()+" is null!");}
	}

	/*Simples Image Element. Geeignet um kleine Grafiken in den Text einzufügen.
		 * Außerhalb von Fließtext ist die @Link render() Methode zu verwenden.*/
	public void renderInline(HtmlElement parent, Image image)
	{
		HtmlElement img = new HtmlElement("img");
		String title = "This is an image.";
		if(image.getTitle() != null) {title = TxtTitleFactory.build(image.getTitle());}
		img.setAttribute("alt", title);
		img.setAttribute("src", cp.getCrossMediaManager().getImageRef(image.getMedia()));
		if(image.isSetHeight()|| image.isSetWidth()){img.setStyleAttribute(HtmlElement.evaluateSize(image.getWidth()) + HtmlElement.evaluateSize(image.getHeight()));}

		parent.addContent(img);
	}

	/*Figure zum Einfügen von Bildern/Grafiken unabhängig vom Fließtext.*/
	public void render(HtmlElement parent, Image image)
	{
		String title = "This is an image.";
		if(image.getTitle() != null){title = "Image " + HtmlSectionRenderer.sectionCount + "." + imgcount + ": " + TxtTitleFactory.build(image.getTitle());}
		imgcount++;
		
		HtmlElement figure = new HtmlElement("figure");
		HtmlElement img = new HtmlElement("img");
		img.setAttribute("id", image.getId());
		img.setAttribute("alt", title);
		
		if(image.getMedia()!=null)
		{
			img.setAttribute("src", cp.getCrossMediaManager().getImageRef(image.getMedia()));
		}
		
		if(image.isSetHeight()|| image.isSetWidth()){img.setStyleAttribute(HtmlElement.evaluateSize(image.getWidth()) + HtmlElement.evaluateSize(image.getHeight()));}
		if(image.isSetAlignment() && image.getAlignment().getHorizontal().equals("center"))
		{
			figure.setStyleAttribute("display:block;margin-left: auto;margin-right: auto; " + HtmlElement.evaluateSize(image.getWidth(), 2.0));
			img.getAttribute("style").setValue(img.getAttribute("style").getValue() + "display:block;margin-left: auto;margin-right: auto;");
		}
		figure.addContent(img);
		if(!TxtTitleFactory.build(image.getTitle()).isEmpty())
		{
			figure.addContent(caption(title));
		}
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
