package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;


public class MdEmphasisRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	public MdEmphasisRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Emphasis emphasis)
	{
		boolean bold = emphasis.isSetBold() && emphasis.isBold();
		boolean italic = emphasis.isSetItalic() && emphasis.isItalic();
		boolean quote = emphasis.isSetQuote() && emphasis.isQuote();

		if(bold)
		{

		}
		if(italic)
		{

		}
		if(quote)
		{

		}
	}
}
