package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
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
		if(emphasis.isBold()) {preTxt.add("\\textbf{");}
		sb.append(emphasis.getValue());
		if(emphasis.isBold()){postTxt.add("}");}
		
		addString(sb.toString());
	}
}
