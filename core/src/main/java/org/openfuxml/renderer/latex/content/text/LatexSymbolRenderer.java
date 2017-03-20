package org.openfuxml.renderer.latex.content.text;

import org.openfuxml.content.text.Symbol;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSymbolRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSymbolRenderer.class);
	
	public LatexSymbolRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Symbol symbol) throws OfxAuthoringException
	{	
		txt.add("\\"+symbol.getCode());
	}
}
