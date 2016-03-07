package org.openfuxml.renderer.html.structure;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class HtmlListingRenderer extends AbstractOfxHtmlRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(HtmlListingRenderer.class);


	@Deprecated //ToDo delete all deprecated constructor
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
		HtmlElement pre = HtmlElement.preFormatted();

		if(listing.isSetRaw()){code.addContent(listing.getRaw().getValue());}
		if(listing.isSetExternal()){
			try {
				code.addContent(getExtrernal(listing.getExternal()));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		pre.setAttribute("style", "border: 1px solid gray;background:#EFF9FF;display: inline-flex;padding: 5px");
		parent.addContent(pre.addContent(code));
	}

	public String getExtrernal(String ex) throws IOException {
		File f = new File(ex);
		String tmp ="";
		BufferedReader br = new BufferedReader(new FileReader(f));
		String input = br.readLine();
		while (input != null)
		{
			tmp += input+"\n";
			input = br.readLine();
		}
		return tmp;
	}

}
