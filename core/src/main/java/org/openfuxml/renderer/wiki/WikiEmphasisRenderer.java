package org.openfuxml.renderer.wiki;

import java.util.Objects;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;

public class WikiEmphasisRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer
{
	public WikiEmphasisRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	/**
	 * Transform the XML child into wiki syntax
	 * @param emphasis Emphasis markup
	 * @throws OfxAuthoringException exception
	 */
	public void render(Emphasis emphasis) throws OfxAuthoringException
	{
		boolean bold = Objects.nonNull(emphasis.isBold()) && emphasis.isBold();
		boolean italic = Objects.nonNull(emphasis.isItalic()) && emphasis.isItalic();
		
		if(logger.isTraceEnabled())
		{
			logger.debug("bold: "+bold);
			logger.debug("italic: "+italic);
		}
		
		//TODO
		StringBuffer sb = new StringBuffer();
		if(bold) {sb.append("'''");}
		if(italic) {sb.append("''");}
		
		sb.append(emphasis.getValue());
		
		if(bold){sb.append("'''");}
		if(italic){sb.append("''");}
		
		txt.add(sb.toString());
	}
}