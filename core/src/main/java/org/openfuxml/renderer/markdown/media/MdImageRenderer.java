package org.openfuxml.renderer.markdown.media;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MdImageRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdImageRenderer.class);

	public MdImageRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Image image)
	{
		StringBuffer sb = new StringBuffer();

		String title = TxtTitleFactory.build(image.getTitle());

		sb.append("\n\n![");
		sb.append(title);
		sb.append("](");
		sb.append(image.getMedia().getSrc().trim());
		sb.append(")  \n"); //Two spaces before linebreak are important to move the title below the image
		sb.append(caption(title));
		txt.add(sb.toString());
	}

	private String caption(String title)
	{
		return new StringBuffer().append("*").append(title).append("*\n\n").toString();
	}

	public void renderInline(Image image)
	{
		StringBuffer sb = new StringBuffer();

		String title = TxtTitleFactory.build(image.getTitle());

		sb.append(" ![");
		sb.append(title);
		sb.append("](");
		sb.append(image.getMedia().getSrc());
		sb.append(") ");
		txt.add(sb.toString());
	}
}
