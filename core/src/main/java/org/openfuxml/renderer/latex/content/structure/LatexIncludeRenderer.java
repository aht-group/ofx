package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Include;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexIncludeRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexIncludeRenderer.class);
	
	public LatexIncludeRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Class<?> parent, String include, boolean postBlankLine) throws OfxAuthoringException
	{
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+parent.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		if(dsm.includeEscapeTexlipse()){txt.add("%###");}
		StringBuffer sb = new StringBuffer();
		sb.append("\\input{").append(include).append("}");
		txt.add(sb.toString());
		if(dsm.includeEscapeTexlipse()){txt.add("%###");}
		
		if(postBlankLine){txt.add("");}
	}
	
	public void render(Include include) throws OfxAuthoringException
	{
		if(dsm.includeEscapeTexlipse()){txt.add("%###");}
		StringBuffer sb = new StringBuffer();
		sb.append("\\input{").append(include.getValue()).append("}");
		txt.add(sb.toString());
		if(dsm.includeEscapeTexlipse()){txt.add("%###");}
	}
}
