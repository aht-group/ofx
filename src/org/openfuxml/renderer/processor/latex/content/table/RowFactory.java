package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Row;
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
	
	public void render(Row row)
	{	
		boolean addCell=false;
		
		for(Cell cell : row.getCell())
		{
			if(addCell){renderer.add(new StringRenderer("&"));}
			addCell = true;
			renderCell(cell);
		}
	}
	
	private void renderCell(Cell cell)
	{
		for(Object s : cell.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s);}
			else {logger.warn("No Renderer for "+s.getClass().getSimpleName());}
		}
	}
}