package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;

import java.io.IOException;
import java.io.Writer;

public class MdSectionRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	public MdSectionRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	private void renderListing(Listing listing)
	{
		MdListingRenderer r = new MdListingRenderer(cmm,dsm);
		r.render(listing);
		renderer.add(r);
	}

	@Override
	public void write(Writer w) throws IOException
	{

	}
}
