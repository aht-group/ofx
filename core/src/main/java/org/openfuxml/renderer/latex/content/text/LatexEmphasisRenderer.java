package org.openfuxml.renderer.latex.content.text;

import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexEmphasisRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexEmphasisRenderer.class);
	
	public LatexEmphasisRenderer(ConfigurationProvider cp)
	{
		super(cp);
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
		
		StringBuffer sb = new StringBuffer();
		if(quote){sb.append("\\enquote{");}
		if(typewriter){sb.append("\\texttt{");}
		if(bold) {sb.append("\\textbf{");}
		if(italic) {sb.append("\\textit{");}
		sb.append(TexSpecialChars.replace(emphasis.getValue()));
		if(bold){sb.append("}");}
		if(italic){sb.append("}");}
		if(typewriter){sb.append("}");}
		if(quote){sb.append("}");}
		
		txt.add(sb.toString());
	}
}
