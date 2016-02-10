package org.openfuxml.renderer.markdown.structure;


import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdListRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(MdListRenderer.class);

	static String item ="\n";
	public MdListRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(List list)
	{
		txt.add(item);
		if(list.getType().isSetOrdering() && list.getType().getOrdering().equals("ordered"))
		{
			int iterator = 1;
			for(Item i : list.getItem())
			{
				item += iterator + ". ";
				MdItemRenderer iRender = new MdItemRenderer(cmm, dsm);
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
				MdItemRenderer iRender = new MdItemRenderer(cmm, dsm);
				iRender.render(i);
				txt.add(item);
				item = "\n";
				renderer.add(iRender);
			}
		}
	}
}
