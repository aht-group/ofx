package org.openfuxml.renderer.processor.latex.content.table;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.xml.content.list.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCellFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexCellFactory.class);
	
	public LatexCellFactory()
	{
		
	}
	
	public void render(Cell cell) throws OfxAuthoringException
	{	
		for(Object s : cell.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof List){renderList((List)s,this);}
			else {logger.warn("No Renderer for "+s.getClass().getSimpleName());}
		}
	}
}