package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.ofx.Emphasis;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.content.ofx.table.Row;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexRowRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexRowRenderer.class);
	
	private Emphasis emphasisOverride;
	
	public LatexRowRenderer(){this(null);}
	public LatexRowRenderer(Emphasis emphasis)
	{
		this.emphasisOverride=emphasis;
		postTxt.add("\\\\");
	}
	
	public void render(Row row) throws OfxAuthoringException
	{	
		boolean firstCell=true;
		
		for(Cell cell : row.getCell())
		{
			if(!firstCell){renderer.add(new StringRenderer("&"));}
			firstCell = false;
			
			LatexCellRenderer f = new LatexCellRenderer(emphasisOverride);
			f.render(cell);
			renderer.add(f);
		}
	}
}