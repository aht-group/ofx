package org.openfuxml.renderer.latex.content.text;

import org.openfuxml.content.ofx.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexEmphasisRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexEmphasisRenderer.class);
	
	
	public LatexEmphasisRenderer()
	{
		
	}
	
	
	public void render(Emphasis emphasis) throws OfxAuthoringException
	{
		boolean bold = emphasis.isSetBold() && emphasis.isBold();
		boolean italic = emphasis.isSetItalic() && emphasis.isSetItalic();
		
		StringBuffer sb = new StringBuffer();
		if(bold) {sb.append("\\textbf{");}
		if(italic) {sb.append("\\textit{");}
		sb.append(TexSpecialChars.replace(emphasis.getValue()));
		if(bold){sb.append("}");}
		if(italic){sb.append("}");}
		
		txt.add(sb.toString());
	}
}
