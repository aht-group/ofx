package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public LatexTableRenderer()
	{

	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		LatexGridTableRenderer gtf = new LatexGridTableRenderer();
		gtf.render(table);
		renderer.add(gtf);
	}
}
