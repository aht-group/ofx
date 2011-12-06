package org.openfuxml.renderer.processor.latex.content.table;

import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.StringRenderer;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RowFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(RowFactory.class);
	
	public RowFactory()
	{
		postTxt.add("\\\\");
	}
	
	public void render(Row row) throws OfxAuthoringException
	{	
		boolean addCell=false;
		
		for(Cell cell : row.getCell())
		{
			if(addCell){renderer.add(new StringRenderer("&"));}
			addCell = true;
			
			CellFactory f = new CellFactory();
			f.render(cell);
			renderer.add(f);
		}
	}
}