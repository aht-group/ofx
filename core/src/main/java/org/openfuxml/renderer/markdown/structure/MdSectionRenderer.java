package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class MdSectionRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	int lvl;

	public MdSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl)
	{
		super(cmm, dsm);
		this.lvl = lvl;
	}

	public void render(Section section)
	{
		for(Object s : section.getContent())
		{
			if(s instanceof Title)
			{
				MdTitleRenderer titleR = new MdTitleRenderer(cmm, dsm);
				titleR.render((Title)s,lvl);
				renderer.add(titleR);
			}
		}
		for(Object o : section.getContent())
		{
			if(!(o instanceof String) && !(o instanceof Title))
			{
				if(o instanceof Section){render((Section)o);}
				else if(o instanceof List){listRenderer((List)o, this);}
			}
			else {logger.warn("No Renderer for Element "+o.getClass().getSimpleName());}
		}
	}
}
