package org.openfuxml.interfaces.renderer.latex;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.renderer.util.TabluarWidthCalculator;
import org.openfuxml.model.xml.core.table.Specification;
import org.openfuxml.model.xml.core.table.Table;

public interface OfxLatexTableRenderer extends OfxLatexRenderer
{
	String buildTabularCols(TabluarWidthCalculator tabularWidthCalculator, Specification spec);
	void render(Table table) throws OfxAuthoringException;
}
