package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class MdCommentRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdCommentRenderer.class);

	public MdCommentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Comment comment)
	{

		for(Raw raw : comment.getRaw())
		{
			txt.add(comment(raw.getValue()));
		}
	}

	private String comment(String value)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n[//]:#(");
		sb.append(value);
		sb.append(")");
		return sb.toString();
	}
}
