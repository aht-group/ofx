package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.content.StringRenderer;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class RowFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(RowFactory.class);
	
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