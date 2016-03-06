package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.layout.Font;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.table.Row;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.text.StringRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexRowRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexRowRenderer.class);
	
	private Emphasis emphasisOverride; public void setEmphasisOverride(Emphasis emphasisOverride) {this.emphasisOverride = emphasisOverride;}
	private Font font; public void setFont(Font font) {this.font = font;}
	
	public LatexRowRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		postTxt.add("\\\\");
	}
	
	public void render(Row row) throws OfxAuthoringException
	{	
		boolean firstCell=true;
		
		for(Cell cell : row.getCell())
		{
			if(!firstCell){renderer.add(new StringRenderer("&"));}
			firstCell = false;
			
			LatexCellRenderer f = new LatexCellRenderer(cmm,dsm);
			f.setEmphasisOverride(emphasisOverride);
			f.setFont(font);
			
			f.render(cell);
			renderer.add(f);
		}
	}
}