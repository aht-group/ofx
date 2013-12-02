package org.openfuxml.renderer.latex.content.listing;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.TexSpecialChars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexListingRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexListingRenderer.class);
	
	
	public LatexListingRenderer()
	{
	}
	
	public void render(Paragraph paragraph) throws OfxAuthoringException
	{
		for(Object o : paragraph.getContent())
		{
			if(o==null){throw new OfxAuthoringException("paragraph has no content");}
			if(o instanceof String){addString((String)o);}
			else {logger.warn("Unknown object: "+o.getClass().getCanonicalName());}
		}
	}
	
	private void addString(String s)
	{
		txt.add(TexSpecialChars.replace(s));
	}
}
