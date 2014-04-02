package org.openfuxml.interfaces.latex;

import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;

public interface OfxLatexTableRenderer extends OfxLatexRenderer
{
	void render(Table table) throws OfxAuthoringException;
}
