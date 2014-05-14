package org.openfuxml.renderer.latex.content.media;

import javax.print.attribute.standard.Media;

import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.media.Image;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.interfaces.CrossMediaManager;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.media.cross.NoOpCrossMediaManager;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexImageRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexImageRenderer.class);
		
	private CrossMediaManager cmm;
	
	public LatexImageRenderer()
	{	
		cmm = new NoOpCrossMediaManager();
	}
	
	public LatexImageRenderer(CrossMediaManager cmm)
	{	
        this.cmm=cmm;
	}
	
	public void render(Image image) throws OfxAuthoringException
	{
		if(!image.isSetMedia()){throw new OfxAuthoringException(Image.class.getSimpleName()+" has no "+Media.class.getSimpleName());}
		
		renderPre(image);

		if(image.isSetAlignment()){alignment(image.getAlignment());}
		
		StringBuffer sbIncludeGraphics = new StringBuffer();
		sbIncludeGraphics.append("  \\includegraphics");
		sbIncludeGraphics.append(layout());
		sbIncludeGraphics.append("{").append(cmm.getImageRef(image.getMedia())).append("}");
		txt.add(sbIncludeGraphics.toString());
		
		renderPost(image);
	}
	
	private void renderPre(Image image) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Image.class.getSimpleName()+" with "+this.getClass().getSimpleName()));
		
		if(image.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer();
			rComment.render(image.getComment());
			renderer.add(rComment);
		}

		preTxt.add("");
		preTxt.add("\\begin{figure}");
	}
	
	private void renderPost(Image image)
	{
		if(image.isSetTitle()) {postTxt.add("  \\caption{"+image.getTitle().getValue()+"}");}
		if(image.isSetId())    {postTxt.add("  \\label{"+image.getId()+"}");}
		postTxt.add("\\end{figure}");
	}
	
	private String layout()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[width=12cm]");
		return sb.toString();
	}
	
	private void alignment(Alignment alignment)
	{
		if(alignment.isSetHorizontal())
		{
			XmlAlignmentFactory.Horizontal horizontal = XmlAlignmentFactory.Horizontal.valueOf(alignment.getHorizontal());
			switch (horizontal)
			{
				case center: txt.add("  \\center");break;
				default:	logger.warn("Alignment (horizontal): "+horizontal.toString()+" not handled");
			}
		}
	}
}