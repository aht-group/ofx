package org.openfuxml.renderer.latex.content.structure;

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
		StringBuffer sb = new StringBuffer();
		if(emphasis.isBold()) {sb.append("\\textbf{");}
		sb.append(TexSpecialChars.replace(emphasis.getValue()));
		if(emphasis.isBold()){sb.append("}");}
		
		txt.add(sb.toString());
	}
}
