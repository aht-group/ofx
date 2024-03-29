package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.model.xml.core.ofx.Comment;
import org.openfuxml.model.xml.core.ofx.Raw;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class MdCommentRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdCommentRenderer.class);

	public static boolean first = true;

	@Deprecated
	public MdCommentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public MdCommentRenderer(ConfigurationProvider cp) {
		super(cp);
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
		if(first){first=false; sb.append("\n");}
		sb.append("\n[//]:# (");
		sb.append(value.trim());
		sb.append(")");
		return sb.toString();
	}
}
