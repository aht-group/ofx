package org.openfuxml.renderer.html.structure;
/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.list.Item;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.renderer.markdown.structure.MdListRenderer;
import org.openfuxml.renderer.markdown.structure.MdParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlItemRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlItemRenderer.class);
	@Deprecated
	public HtmlItemRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlItemRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	/*Erstellt <li> tags für Listeneinträge und übergibt sie zurück an das
	HtmlElement der Liste*/
	public void render(HtmlElement list, Item item, boolean isDescription)
	{
		if(isDescription){render(list, item);}
		else
			{
				HtmlElement li = new HtmlElement("li");
				processContent(li, item);
				list.addContent(li);
			}
	}

	/*Erstellt anstelle von <li> tags die <dd> und <dt> tags für Definitionslisten */
	private void render(HtmlElement list, Item item)
	{
		HtmlElement dd = new HtmlElement("dd"); //dd & dt alternate
		dd.addContent(item.getName());
		HtmlElement dt = new HtmlElement("dt");
		processContent(dt, item);
		list.addContent(dd);list.addContent(dt);
	}

	/* Verarbeiten des Item Inhaltes. <p> tags innerhalb eines Listen-tags sind zwar
	möglich, verbrauchen aber unnötig Platz, daher wird nur der Inhalt der Paragraph
	Objekte verarbeitet. */
	private void processContent(HtmlElement element, Item item)
	{
		for(Object o : item.getContent())
		{
			if(o instanceof Paragraph){paragraphContentRenderer(element, ((Paragraph)o));}
			else if(o instanceof String){element.addContent(((String)o));}
		}
	}
}
