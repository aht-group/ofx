package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Rebecca Roblick
 */
public class HtmlCommentRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlCommentRenderer.class);

	public HtmlCommentRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}

	public void render(HtmlElement parent, Comment comment)
	{
		for(Raw raw : comment.getRaw()){parent.addContent(new org.jdom2.Comment(raw.getValue()));}
	}
}
