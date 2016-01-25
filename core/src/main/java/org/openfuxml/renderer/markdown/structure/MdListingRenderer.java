package org.openfuxml.renderer.markdown.structure;


import org.openfuxml.content.ofx.Listing;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.markdown.OfxMarkdownRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;

import java.io.IOException;
import java.io.Writer;

public class MdListingRenderer extends AbstractOfxMarkdownRenderer implements OfxMarkdownRenderer
{

	public MdListingRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Listing listing)
	{

	}

	@Override
	public void write(Writer w) throws IOException
	{

	}
}
