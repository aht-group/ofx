package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.model.xml.core.table.Row;
import org.openfuxml.model.xml.core.text.Emphasis;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexRowRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexRowRenderer.class);
	
	private Emphasis emphasisOverride; public void setEmphasisOverride(Emphasis emphasisOverride) {this.emphasisOverride = emphasisOverride;}
	private Font font; public void setFont(Font font) {this.font = font;}
	
	public LatexRowRenderer(ConfigurationProvider cp)
	{
		super(cp);
		postTxt.add("\\\\");
	}
	
	public void render(Row row) throws OfxAuthoringException
	{	
		boolean firstCell=true;
		
		for(Cell cell : row.getCell())
		{
			if(!firstCell){renderer.add(new StringRenderer("&"));}
			firstCell = false;
			
			LatexCellRenderer f = new LatexCellRenderer(cp);
			f.setEmphasisOverride(emphasisOverride);
			f.setFont(font);
			
			f.render(cell);
			renderer.add(f);
		}
	}
}