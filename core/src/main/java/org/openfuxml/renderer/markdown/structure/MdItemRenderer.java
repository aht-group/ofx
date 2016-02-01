package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdItemRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdItemRenderer.class);
	public MdItemRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Item item, OfxMdRenderer parent)
	{
		for(Object s : item.getContent())
		{
			if     (!(s instanceof String))
			{
			if(s instanceof Paragraph){/*paragraphRenderer((Paragraph)s,false);*/}
			if(s instanceof List){listRenderer((List)s, parent);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
			}

		}
	}
}
