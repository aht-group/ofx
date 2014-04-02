package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public static enum Type {grid,line}
	
	public LatexTableRenderer()
	{

	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		OfxLatexTableRenderer tableRenderer;
		Type type = Type.line;
		
		switch(type)
		{
			case line: tableRenderer = new LatexLineTableRenderer();break;
			default: tableRenderer = new LatexGridTableRenderer();break;
		}
		
		tableRenderer.render(table);
		renderer.add(tableRenderer);
	}
}
