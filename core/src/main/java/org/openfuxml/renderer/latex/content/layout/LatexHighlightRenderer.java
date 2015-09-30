package org.openfuxml.renderer.latex.content.layout;

import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexHighlightRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexHighlightRenderer.class);
	
	public LatexHighlightRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Highlight highlight) throws OfxAuthoringException, OfxConfigurationException
	{
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Highlight.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		preTxt.add("");
		preTxt.add("\\begin{shaded}");
		
		int index = 0;
		for(Object s : highlight.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,index!=0);index++;}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}

		postTxt.add("\\end{shaded}");
	}
}
