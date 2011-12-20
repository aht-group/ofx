package org.openfuxml.renderer.processor.latex.content.table;

import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.StringRenderer;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexRowFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexRowFactory.class);
	
	public LatexRowFactory()
	{
		postTxt.add("\\\\");
	}
	
	public void render(Row row) throws OfxAuthoringException
	{	
		boolean firstCell=true;
		
		for(Cell cell : row.getCell())
		{
			if(!firstCell){renderer.add(new StringRenderer("&"));}
			firstCell = false;
			
			LatexCellFactory f = new LatexCellFactory();
			f.render(cell);
			renderer.add(f);
		}
	}
}