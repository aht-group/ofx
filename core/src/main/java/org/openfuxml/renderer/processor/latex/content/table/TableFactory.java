package org.openfuxml.renderer.processor.latex.content.table;

import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(TableFactory.class);
	
	public TableFactory()
	{

	}
	
	public void render(Table table) throws OfxAuthoringException
	{	
		GridTableFactory gtf = new GridTableFactory();
		gtf.render(table);
		renderer.add(gtf);
	}
}
