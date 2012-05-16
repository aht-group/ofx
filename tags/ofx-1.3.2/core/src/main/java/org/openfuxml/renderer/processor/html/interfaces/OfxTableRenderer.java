package org.openfuxml.renderer.processor.html.interfaces;

import org.jdom.Element;
import org.openfuxml.content.ofx.table.Table;

public interface OfxTableRenderer
{
	public Element render(Table table);
}