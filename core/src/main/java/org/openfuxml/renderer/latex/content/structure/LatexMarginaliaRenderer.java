package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexMarginaliaRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexMarginaliaRenderer.class);
	
	public LatexMarginaliaRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Marginalia marginalia) throws OfxAuthoringException
	{
//		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Highlight.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));

		preTxt.add("\\marginnote{");
		
		int index = 0;
		for(Object s : marginalia.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,index!=0);index++;}
			else if(s instanceof Image){renderImage((Image)s);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}

		postTxt.add("}");
	}
}
