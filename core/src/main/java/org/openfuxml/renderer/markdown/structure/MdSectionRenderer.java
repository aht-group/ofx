package org.openfuxml.renderer.markdown.structure;

import java.util.Objects;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdSectionRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdSectionRenderer.class);

	int lvl;

	@Deprecated
	public MdSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm, int lvl)
	{
		super(cmm, dsm);
		this.lvl = lvl;
	}

	public MdSectionRenderer(ConfigurationProvider cp, int lvl) {
		super(cp);
		this.lvl = lvl;
	}

	public void render(Section section)
	{
		//Comments always on top!
		for(Object s : section.getContent())
		{
			if(s instanceof Comment){renderComment((Comment)s);}
		}

		//Title always immediately after comments
		for(Object s : section.getContent())
		{
			if(s instanceof Title)
			{
				MdTitleRenderer titleR = new MdTitleRenderer(cp);
				titleR.render((Title)s,lvl);
				renderer.add(titleR);
			}
		}
		for(Object o : section.getContent())
		{
			if(o instanceof Section){renderSection((Section)o);}
			else if(o instanceof String){txt.add(((String)o).trim());}
			else if(o instanceof List)
			{
				if(Objects.nonNull(((List)o).getComment()))
				{
					MdCommentRenderer.first = true;
					renderComment(((List)o).getComment());
				}
				listRenderer((List)o);
			}
			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o);}
			else if(o instanceof Image){imageRenderer((Image)o);}
		}
	}

	private void renderSection(Section section)
	{
		MdCommentRenderer.first = true;
		MdSectionRenderer sr = new MdSectionRenderer(cp,lvl+1);
		sr.render(section);
		renderer.add(sr);
	}

	private void renderComment(Comment comment)
	{
		MdCommentRenderer commentR = new MdCommentRenderer(cp);
		commentR.render(comment);
		renderer.add(commentR);
	}
}
