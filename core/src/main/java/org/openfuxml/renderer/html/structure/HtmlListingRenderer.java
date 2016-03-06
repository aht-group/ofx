package org.openfuxml.renderer.html.structure;


import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListingRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(HtmlListingRenderer.class);

	@Deprecated
	public HtmlListingRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public HtmlListingRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(HtmlElement parent, Listing listing)
	{
		HtmlElement code = new HtmlElement("code");
		code.addContent(listing.getRaw().getValue());
		HtmlElement pre = HtmlElement.preFormatted();
		pre.setAttribute("style", "border: 1px solid gray;background:#eef");
		parent.addContent(pre.addContent(code));
	}

}
