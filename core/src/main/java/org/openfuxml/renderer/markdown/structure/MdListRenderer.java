package org.openfuxml.renderer.markdown.structure;


import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdListRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(MdListRenderer.class);

	static String item ="";
	public MdListRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(List list, OfxMdRenderer parent)
	{

		if(list.getType().isSetOrdering() && list.getType().getOrdering().equals("ordered"))
		{
			int iterator = 1;
			for(Item i : list.getItem())
			{
					item += iterator + ". ";
					MdItemRenderer iRender = new MdItemRenderer(cmm, dsm);
					iRender.render(i, this);
					renderer.add(iRender);
					txt.add(item);
					item = "";
					iterator++;
			}
		}
		else
		{
			for(Item i : list.getItem())
			{
					item += "* ";
					MdItemRenderer iRender = new MdItemRenderer(cmm, dsm);
					iRender.render(i, this);
					txt.add(item);
					item = "";
					renderer.add(iRender);
			}
		}
	}

}
