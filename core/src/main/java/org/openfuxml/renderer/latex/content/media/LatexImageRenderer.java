package org.openfuxml.renderer.latex.content.media;

import java.util.Objects;

import javax.print.attribute.standard.Media;

import org.openfuxml.content.layout.Alignment;
import org.openfuxml.content.media.Image;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.layout.XmlAlignmentFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.layout.LatexColumnRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexMarginaliaRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexParagraphRenderer;
import org.openfuxml.renderer.latex.content.structure.LatexTitleRenderer;
import org.openfuxml.renderer.latex.content.table.LatexCellRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.openfuxml.renderer.latex.util.LatexWidthCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexImageRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexImageRenderer.class);

	private enum Environment{Figure,Cell,Inline,Margin}
	
	private Environment environment;
	
	private LatexWidthCalculator lwc;
	
	public LatexImageRenderer(ConfigurationProvider cp)
	{	
		super(cp);
		lwc = new LatexWidthCalculator();
	}
	
	public void render(Object parentRenderer, Image image) throws OfxAuthoringException
	{
		setEnvironment(parentRenderer);
		
		if(Objects.isNull(image.getMedia())) {throw new OfxAuthoringException(Image.class.getSimpleName()+" has no "+Media.class.getSimpleName());}
		
		renderPre(image);

		StringBuffer sb = new StringBuffer();
		
		if(Objects.nonNull(image.getAlignment()))
		{
			logger.trace("Setting Alignment for "+environment);
			if(Environment.Figure.equals(environment)){txt.add("  "+alignment(image.getAlignment()));}
			else if(Environment.Margin.equals(environment)){sb.append(alignment(image.getAlignment()));}
		}
		
		if(Environment.Cell.equals(environment)){sb.append("$\\vcenter{\\hbox{");}
		sb.append(" \\includegraphics");
		sb.append(imageArguments(parentRenderer,image));
		sb.append("{").append(cmm.getImageRef(image.getMedia())).append("}");
		if(Environment.Cell.equals(environment)){sb.append("}}$");}
		txt.add(sb.toString());
		
		renderPost(image);
	}
	
	private void setEnvironment(Object parent)
	{
		logger.trace("Parent renderer is "+parent.getClass().getSimpleName());
		
		if(parent instanceof LatexCellRenderer) {environment=Environment.Cell;}
		else if (parent instanceof LatexParagraphRenderer){environment=Environment.Inline;}
		else if (parent instanceof LatexMarginaliaRenderer){environment=Environment.Margin;}
		else if (parent instanceof LatexColumnRenderer){environment=Environment.Inline;}
		else {environment=Environment.Figure;}
	}
	
	private void renderPre(Image image) throws OfxAuthoringException
	{
		if(Environment.Figure.equals(environment))
		{
			preTxt.addAll(LatexCommentRenderer.stars());
			preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Image.class.getSimpleName()+" (environment:"+environment+") with "+this.getClass().getSimpleName()));
			
			if(Objects.nonNull(image.getComment()))
			{
				LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
				rComment.render(image.getComment());
				renderer.add(rComment);
			}
			preTxt.add("\\begin{figure}[!htbp]");
		}
	}
	
	private void renderPost(Image image)
	{
		if(Environment.Figure.equals(environment))
		{
			if(Objects.nonNull(image.getTitle()))
			{
				LatexTitleRenderer stf = new LatexTitleRenderer(cp);
				stf.render(image);
				postTxt.addAll(stf.getContent());
			}
			if(Objects.nonNull(image.getId())) {postTxt.add("  \\label{"+image.getId()+"}");}
			postTxt.add("\\end{figure}");
			postTxt.add("");
		}
		
	}
	
	private String imageArguments(Object parentRenderer, Image image) throws OfxAuthoringException
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		if(Objects.nonNull(image.getWidth()) && Objects.nonNull(image.getHeight()))
		{
			throw new OfxAuthoringException("Image HEIGHT and WEIGHT at the same time currently not supported");
		}
		else if(Objects.nonNull(image.getWidth()))
		{
			sb.append("width=");
			sb.append(lwc.buildWidth(parentRenderer,image.getWidth()));
		}
		else if(Objects.nonNull(image.getHeight()))
		{
			sb.append("height=");
			sb.append(lwc.buildHeight(image.getHeight()));
		}
		else {sb.append("width=12cm");}
				
		sb.append("]");
		return sb.toString();
	}
	
	private String alignment(Alignment alignment)
	{
		if(Objects.nonNull(alignment.getHorizontal()))
		{
			XmlAlignmentFactory.Horizontal horizontal = XmlAlignmentFactory.Horizontal.valueOf(alignment.getHorizontal());
			switch (horizontal)
			{
				case center: return("\\center");
				default:	logger.warn("Alignment (horizontal): "+horizontal.toString()+" not handled");
			}
		}
		else
		{
			logger.warn("Alignment not supported");
		}
		return "";
	}
}
