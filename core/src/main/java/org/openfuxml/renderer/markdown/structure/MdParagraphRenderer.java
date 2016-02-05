package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.renderer.markdown.media.MdImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MdParagraphRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdParagraphRenderer.class);

	public MdParagraphRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Paragraph paragraph)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for(Object o : paragraph.getContent())
		{
			if(o instanceof String){sb.append((String)o);}
			else if(o instanceof Emphasis){rendererEmphasis(sb, (Emphasis)o);}
			else if(o instanceof Image){rendererImage(sb, (Image)o);}
		}

		txt.add(sb.toString());
	}

	private void rendererImage(StringBuffer sb, Image image)
	{
		MdImageRenderer iRenderer = new MdImageRenderer(cmm,dsm);
		iRenderer.renderInline(image);
		for(String s : iRenderer.getContent()){sb.append(s);}
	}

	private void rendererEmphasis(StringBuffer sb, Emphasis emphasis)
	{
		MdEmphasisRenderer emRenderer = new MdEmphasisRenderer(cmm, dsm);
		emRenderer.render(emphasis);
		for(String s : emRenderer.getContent()){sb.append(s);}
	}
}
