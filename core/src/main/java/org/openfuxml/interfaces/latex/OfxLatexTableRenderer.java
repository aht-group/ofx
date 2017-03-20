package org.openfuxml.interfaces.latex;

import org.openfuxml.content.table.Specification;
import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.interfaces.renderer.util.TabluarWidthCalculator;

public interface OfxLatexTableRenderer extends OfxLatexRenderer
{
	String buildTabularCols(TabluarWidthCalculator tabularWidthCalculator, Specification spec);
	void render(Table table) throws OfxAuthoringException;
}
