package org.openfuxml.renderer.latex.content.media;

import javax.print.attribute.standard.Media;

import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.media.Image;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.interfaces.CrossMediaManager;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.table.LatexCellRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.util.LatexWidthCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexImageRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexImageRenderer.class);
		
	private boolean inFigure;
	
	public LatexImageRenderer(CrossMediaManager cmm)
	{	
		super(cmm);
	}
	
	public void render(Object parent, Image image) throws OfxAuthoringException
	{
		setEnvironment(parent);
		
		if(!image.isSetMedia()){throw new OfxAuthoringException(Image.class.getSimpleName()+" has no "+Media.class.getSimpleName());}
		
		renderPre(image);

		if(image.isSetAlignment()){alignment(image.getAlignment());}
		
		StringBuffer sb = new StringBuffer();
		if(!inFigure){sb.append("$\\vcenter{\\hbox{");}
		sb.append("  \\includegraphics");
		sb.append(imageArguments(image));
		sb.append("{").append(cmm.getImageRef(image.getMedia())).append("}");
		if(!inFigure){sb.append("}}$");}
		txt.add(sb.toString());
		
		renderPost(image);
	}
	
	private void setEnvironment(Object parent)
	{
		logger.trace("Parent renderer is "+parent.getClass().getSimpleName());
		
		if(parent instanceof LatexCellRenderer) {inFigure = false;}
		else {inFigure = true;}
	}
	
	private void renderPre(Image image) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Image.class.getSimpleName()+" (figure:"+inFigure+") with "+this.getClass().getSimpleName()));
		
		if(image.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer();
			rComment.render(image.getComment());
			renderer.add(rComment);
		}

		if(inFigure){preTxt.add("\\begin{figure}");}
	}
	
	private void renderPost(Image image)
	{
		if(inFigure && image.isSetTitle()) {postTxt.add("  \\caption{"+image.getTitle().getValue()+"}");}
		if(inFigure && image.isSetId())    {postTxt.add("  \\label{"+image.getId()+"}");}
		if(inFigure){postTxt.add("\\end{figure}");}
		postTxt.add("");
	}
	
	private String imageArguments(Image image) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		if(image.isSetWidth() && image.isSetHeight())
		{
			throw new OfxAuthoringException("Image HEIGHT and WEIGHT at the same time currently not supported");
		}
		else if(image.isSetWidth())
		{
			sb.append("width=");
			LatexWidthCalculator lwc = new LatexWidthCalculator();
			sb.append(lwc.buildWidth(image.getWidth()));
		}
		else if(image.isSetHeight())
		{
			sb.append("height=");
			LatexWidthCalculator lwc = new LatexWidthCalculator();
			sb.append(lwc.buildHeight(image.getHeight()));
		}
		else {sb.append("width=12cm");}
				
		sb.append("]");
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
