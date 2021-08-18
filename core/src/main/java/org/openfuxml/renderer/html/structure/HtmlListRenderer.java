package org.openfuxml.renderer.html.structure;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final Logger logger = LoggerFactory.getLogger(HtmlListRenderer.class);

	@Deprecated
	public HtmlListRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlListRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, List list)
	{
		HtmlElement element;
		String type = "ul";
		boolean isDescription = false;
		if(list.getType().isSetOrdering() && list.getType().getOrdering().equalsIgnoreCase("ordered")){type = "ol";}
		if(list.getType().isSetDescription() && list.getType().isDescription())
		{
			type = "dl";
			isDescription = true;
		}
		element = new HtmlElement(type);
		if(list.isSetComment()){commentRenderer(element,list.getComment());}
		for(Item i : list.getItem()){itemRenderer(element, i, isDescription);}
		parent.addContent(element);
	}

	private void itemRenderer(HtmlElement list, Item i, boolean isDescription)
	{
		HtmlItemRenderer itemR = new HtmlItemRenderer(cp);
		itemR.render(list, i, isDescription);
	}

}
