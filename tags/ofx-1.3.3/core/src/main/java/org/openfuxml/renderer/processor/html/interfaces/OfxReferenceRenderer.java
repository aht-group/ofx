package org.openfuxml.renderer.processor.html.interfaces;

import org.jdom.Content;
import org.openfuxml.content.ofx.Reference;

public interface OfxReferenceRenderer
{
	public Content render(Reference refernce);
}