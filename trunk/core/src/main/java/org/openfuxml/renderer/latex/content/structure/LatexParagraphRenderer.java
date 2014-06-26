package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexEmphasisRenderer;
import org.openfuxml.renderer.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexParagraphRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexParagraphRenderer.class);
	
	
	public LatexParagraphRenderer(boolean preBlankLine)
	{
		if(preBlankLine){preTxt.add("");}
	}
	
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{	
		StringBuffer sb = new StringBuffer();
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			else if(o instanceof String){sb.append(TexSpecialChars.replace((String)o));}
			else if(o instanceof Emphasis){renderEmphasis(sb, (Emphasis)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
		txt.add(sb.toString());
	}
	
	private void renderEmphasis(StringBuffer sb, Emphasis emphasis) throws OfxAuthoringException
	{
		LatexEmphasisRenderer stf = new LatexEmphasisRenderer();
		stf.render(emphasis);
		for(String s : stf.getContent()){sb.append(s);}
	}
}
