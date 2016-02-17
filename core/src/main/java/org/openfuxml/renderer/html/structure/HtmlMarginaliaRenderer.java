package org.openfuxml.renderer.html.structure;

import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
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
	}

//	public void imageRenderer(HtmlElement parent, Image image)
//	{
//		HtmlImageRenderer iRenderer = new HtmlImageRenderer(cmm, dsm);
//		iRenderer.renderInline(parent, image);
//	}
}
