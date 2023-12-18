package org.openfuxml.renderer.markdown.structure;

import java.util.Objects;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxMdRenderer;
import org.openfuxml.renderer.markdown.AbstractOfxMarkdownRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MdEmphasisRenderer extends AbstractOfxMarkdownRenderer implements OfxMdRenderer
{
	final static Logger logger = LoggerFactory.getLogger(MdEmphasisRenderer.class);

	@Deprecated
	public MdEmphasisRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm){super(cmm, dsm);}

	public MdEmphasisRenderer(ConfigurationProvider cp) {
		super(cp);
	}

	public void render(Emphasis emphasis)
	{
		boolean bold = Objects.nonNull(emphasis.isBold()) && emphasis.isBold();
		boolean italic = Objects.nonNull(emphasis.isItalic()) && emphasis.isItalic();
		boolean typewriter = Objects.nonNull(emphasis.getStyle()) && emphasis.getStyle().equals("typewriter");
		boolean quote = Objects.nonNull(emphasis.isQuote()) && emphasis.isQuote();

		StringBuffer sb = new StringBuffer();
		if(quote){sb.append("\n> ");}
		if(bold){sb.append("**");} // __ (double underscore) may also be used for bold/strong
		if(italic){sb.append("_");}// * may also be used for italic
		if(typewriter){sb.append("`");}
		sb.append(emphasis.getValue());
		if(italic){sb.append("_");}
		if(bold){sb.append("**");} // always close withe the same symbol!
		if(quote){sb.append("\n\n");} // only one linebreak will continue the quote!
		if(typewriter){sb.append("`");}

		txt.add(sb.toString());
	}
}