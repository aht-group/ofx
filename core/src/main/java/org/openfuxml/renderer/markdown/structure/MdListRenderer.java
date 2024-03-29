package org.openfuxml.renderer.markdown.structure;


import java.util.Objects;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdListRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final Logger logger = LoggerFactory.getLogger(MdListRenderer.class);

	static String item ="\n";

	@Deprecated
	public MdListRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public MdListRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(List list)
	{
		txt.add(item);
		if(Objects.nonNull(list.getType().getOrdering()) && list.getType().getOrdering().equals("ordered"))
		{
			int iterator = 1;
			for(Item i : list.getItem())
			{
				item += iterator + ". ";
				MdItemRenderer iRender = new MdItemRenderer(cp);
				iRender.render(i);
				renderer.add(iRender);
				txt.add(item);
				item = "\n";
				iterator++;
			}
		}
		else
		{
			for(Item i : list.getItem())
			{
				item += "* ";
				MdItemRenderer iRender = new MdItemRenderer(cp);
				iRender.render(i);
				txt.add(item);
				item = "\n";
				renderer.add(iRender);
			}
		}
	}
}
