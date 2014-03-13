package org.openfuxml.renderer.processor.html.interfaces;

import org.jdom2.Content;
import org.openfuxml.content.ofx.Reference;

public interface OfxReferenceRenderer
{
	public Content render(Reference refernce);
}