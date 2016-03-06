package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexHighlightRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexHighlightRenderer.class);
	
	public LatexHighlightRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Highlight highlight) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Highlight.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		preTxt.add("");
		preTxt.add("\\begin{shaded}");
		
		int index = 0;
		for(Object s : highlight.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,index!=0);index++;}
			else if(s instanceof Marginalia){renderMarginalia((Marginalia)s);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}

		postTxt.add("\\end{shaded}");
	}
}
