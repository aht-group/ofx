package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.list.List;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class CellFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(CellFactory.class);
	
	public CellFactory()
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