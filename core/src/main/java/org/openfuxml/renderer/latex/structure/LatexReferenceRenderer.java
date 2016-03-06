package org.openfuxml.renderer.latex.structure;

import org.openfuxml.content.ofx.Reference;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexReferenceRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexReferenceRenderer.class);
	
	public LatexReferenceRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Reference reference) throws OfxAuthoringException
	{
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\ref{");
		sb.append(reference.getTarget());
		sb.append("}");
		
		txt.add(sb.toString());
	}
}
