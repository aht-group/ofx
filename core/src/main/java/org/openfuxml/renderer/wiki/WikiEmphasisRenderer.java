package org.openfuxml.renderer.wiki;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;

public class WikiEmphasisRenderer extends AbstractOfxWikiRenderer implements OfxWikiRenderer {
	
	public WikiEmphasisRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Emphasis emphasis) throws OfxAuthoringException
	{
		boolean typewriter = emphasis.isSetStyle() && emphasis.getStyle().equals("typewriter");
		boolean bold = emphasis.isSetBold() && emphasis.isBold();
		boolean italic = emphasis.isSetItalic() && emphasis.isItalic();
		boolean quote = emphasis.isSetQuote() && emphasis.isQuote();
		
		if(logger.isTraceEnabled())
		{
			logger.debug("typewriter: "+typewriter);
			logger.debug("bold: "+bold);
			logger.debug("italic: "+italic);
			logger.debug("quote: "+quote);
		}
		
		//TODO
		StringBuffer sb = new StringBuffer();
//		if(quote){sb.append("");}
//		if(typewriter){sb.append("");}
		if(bold) {sb.append("'''");}
		if(italic) {sb.append("''");}
		
		if(bold){sb.append("'''");}
		if(italic){sb.append("''");}
//		if(typewriter){sb.append("");}
//		if(quote){sb.append("");}
		
		txt.add(sb.toString());
	}
}
