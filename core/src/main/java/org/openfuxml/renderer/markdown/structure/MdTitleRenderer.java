package org.openfuxml.renderer.markdown.structure;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.txt.TxtTitleFactory;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.md.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public class MdTitleRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	private final Logger logger = LoggerFactory.getLogger(MdTitleRenderer.class);

	public MdTitleRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm, dsm);
	}

	public void render(Title title, int lvl)
	{
		logger.trace("Render title");
		if(!title.isSetNumbering()){logger.warn("No numbering for Markdown!");}

		StringBuffer sb = new StringBuffer();

		//Markdown only implements 6 header level! Maybe decreasing max level to 4 because of header design?
		if(lvl >6){lvl = 6;}
		while(sb.length() < lvl){sb.append("#");}

		sb.append(TxtTitleFactory.build(title));
		txt.add(sb.toString());
	}
}