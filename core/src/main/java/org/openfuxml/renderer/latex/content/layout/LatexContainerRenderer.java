package org.openfuxml.renderer.latex.content.layout;

import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Font;
import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.util.LatexFontUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexContainerRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexContainerRenderer.class);
	
	public LatexContainerRenderer(CrossMediaManager cmm, DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Container container) throws OfxAuthoringException
	{
		Font font = null;
		
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Section.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		logger.trace("Render section");
		
		// Comment always on top!
		for(Object o : container.getContent())
		{
			if (o instanceof Comment)
			{
				LatexCommentRenderer rComment = new LatexCommentRenderer(cmm,dsm);
				rComment.render((Comment)o);
				renderer.add(rComment);
			}
			else if(o instanceof Font)
			{
				if(font!=null){throw new OfxAuthoringException("More than one "+Font.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
				font = (Font)o;
			}
		}
				
		if(font!=null){preTxt.add(LatexFontUtil.environmentBegin(font));}
		
		
		for(Object s : container.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof Font){}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}

		postTxt.add("");
		if(font!=null){postTxt.add(LatexFontUtil.environmentEnd());}
	}
}