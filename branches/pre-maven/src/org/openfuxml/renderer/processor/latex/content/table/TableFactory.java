package org.openfuxml.renderer.processor.latex.content.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class TableFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(TableFactory.class);
	
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
