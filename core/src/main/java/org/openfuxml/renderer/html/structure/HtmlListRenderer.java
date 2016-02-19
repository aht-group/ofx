package org.openfuxml.renderer.html.structure;


import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(HtmlListRenderer.class);

	public HtmlListRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(HtmlElement parent, List list)
	{
		//render Comments
		HtmlElement element;
		String type = "ul"; boolean isDescription = false;
		if(list.getType().isSetOrdering() && list.getType().getOrdering().equalsIgnoreCase("ordered")){type = "ol";}
//		if(list.getType().isSetOrdering() && list.getType().getOrdering().equalsIgnoreCase("unordered")){}
		if(list.getType().isSetDescription() && list.getType().isDescription())
		{
			type = "dl";
			isDescription = true;
		}
		element = new HtmlElement(type);
		for(Item i : list.getItem()){stuff(element, i, isDescription);}
		parent.addContent(element);
	}

	private void itemRenderer(HtmlElement list, Item i, boolean isDescription)
	{
		HtmlItemRenderer itemR = new HtmlItemRenderer(cmm, dsm);
		itemR.render(list, i, isDescription);
	}

	private void stuff(HtmlElement element,Item i, boolean isDescription)
	{
		itemRenderer(element, i, isDescription);
	}
}
