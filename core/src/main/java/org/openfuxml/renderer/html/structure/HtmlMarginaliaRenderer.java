package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.openfuxml.renderer.html.media.HtmlImageRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlMarginaliaRenderer extends AbstractOfxHtmlRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(HtmlMarginaliaRenderer.class);

	public HtmlMarginaliaRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(HtmlElement parent, Marginalia marginalia)
	{
		HtmlParagraphRenderer marg = new HtmlParagraphRenderer(cmm,dsm);
		marg.renderAsMarginalia(parent, marginalia);
//		for(Object o : marginalia.getContent())
//		{
//			if(o instanceof String){marg.addContent(((String)o));}
//			else if(o instanceof Paragraph){paragraphRenderer(marg, ((Paragraph)o));}
//			else if(o instanceof Image){imageRenderer(marg, ((Image)o));}
////			else if(o instanceof Container){}
//// 			else if(o instanceof Box){}
//// 			else if(o instanceof Column){}
//		}
//		parent.addContent(marg);
	}

	public void imageRenderer(HtmlElement parent, Image image)
	{
		HtmlImageRenderer iRenderer = new HtmlImageRenderer(cmm, dsm);
		iRenderer.renderInline(parent, image);
	}
}
