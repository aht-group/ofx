package org.openfuxml.renderer.wiki;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;

/**
 * Rendering emphasis(bold,italic) to wiki syntax
 * @author yannkruger
 *
 */
public class WikiEmphasisRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer
{
	public WikiEmphasisRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	/**
	 * Transform the XML child into wiki syntax
	 * @param emphasis Emphasis markup
	 * @throws OfxAuthoringException exception
	 */
	public void render(Emphasis emphasis) throws OfxAuthoringException
	{
		boolean bold = emphasis.isSetBold() && emphasis.isBold();
		boolean italic = emphasis.isSetItalic() && emphasis.isItalic();
		
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