package org.openfuxml.renderer.html.structure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Author: Rebecca Roblick
 */
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxHtmlRenderer;
import org.openfuxml.renderer.html.AbstractOfxHtmlRenderer;
import org.openfuxml.renderer.html.util.HtmlElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlListingRenderer extends AbstractOfxHtmlRenderer implements OfxHtmlRenderer
{
	private final Logger logger = LoggerFactory.getLogger(HtmlListingRenderer.class);
	//ToDo delete all deprecated constructor

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
				code.addContent(getExternal(listing.getExternal()));
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		pre.setAttribute("style", "border: 1px solid gray;background:#EFF9FF;display: inline-flex;padding: 5px");
		parent.addContent(pre.addContent(code));
	}

	public String getExternal(String ex) throws IOException {
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
