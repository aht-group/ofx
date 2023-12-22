package org.openfuxml.renderer.text.table;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxTextRenderer;
import org.openfuxml.model.xml.core.table.Cell;
import org.openfuxml.renderer.text.AbstractOfxTextRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextCellRenderer extends AbstractOfxTextRenderer implements OfxTextRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TextCellRenderer.class);
	
	public TextCellRenderer(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render(Cell cell) throws OfxAuthoringException
	{	
		for(Object s : cell.getContent())
		{
			logger.trace(s.getClass().getName());
			if     (s instanceof String){}
			else if(s instanceof Paragraph) {paragraphRenderer((Paragraph)s);}
			else {logger.warn("No Renderer for "+s.getClass().getSimpleName());}
		}
	}
}
