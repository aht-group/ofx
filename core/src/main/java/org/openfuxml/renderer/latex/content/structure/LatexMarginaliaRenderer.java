package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.layout.Box;
import org.openfuxml.content.layout.Container;
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

		Box box = null;
		
		for(Object o : marginalia.getContent())
		{
			if (o instanceof Box){box=(Box)o;}
		}
		
		StringBuffer sbPre = new StringBuffer();
		sbPre.append("\\marginpar{");
		if(box!=null){sbPre.append("\\fbox{\\parbox{\\marginparwidth}{");}
		
		preTxt.add(sbPre.toString());
		
		int index = 0;
		for(Object o : marginalia.getContent())
		{
			if     (o instanceof String){}
			else if(o instanceof Image){renderImage((Image)o);index++;}
			else if(o instanceof Paragraph){paragraphRenderer((Paragraph)o,index>0);index++;}
			else if(o instanceof Container){containerRenderer((Container)o);}
			else if(o instanceof Box){}
			else {logger.warn("No Renderer for Element "+o.getClass().getSimpleName());}
		}
		
		StringBuffer sbPost = new StringBuffer();
		sbPost.append("}");
		if(box!=null){sbPost.append("}}");}

		postTxt.add(sbPost.toString());
	}
}
