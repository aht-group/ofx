package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.interfaces.latex.OfxLatexTableRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexTableRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexTableRenderer.class);
	
	public static enum Type {grid,line}
	boolean preBlankLine;
	
	public LatexTableRenderer(){this(true);}
	public LatexTableRenderer(boolean preBlankLine)
	{
		this.preBlankLine=preBlankLine;
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
		
		if(preBlankLine){preTxt.add("");}
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a Latex table with: "+tableRenderer.getClass().getSimpleName()));
		if(table.isSetComment())
		{
			LatexCommentRenderer rComment = new LatexCommentRenderer();
			rComment.render(table.getComment());
			renderer.add(rComment);
		}
		
		tableRenderer.render(table);
		renderer.add(tableRenderer);
	}
}
