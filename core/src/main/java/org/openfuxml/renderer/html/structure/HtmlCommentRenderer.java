package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.interfaces.ConfigurationProvider;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HtmlCommentRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlCommentRenderer.class);

	@Deprecated
	public HtmlCommentRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlCommentRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Comment comment)
	{
		for(Raw raw : comment.getRaw()){parent.addContent(new org.jdom2.Comment(raw.getValue()));}
	}
}
