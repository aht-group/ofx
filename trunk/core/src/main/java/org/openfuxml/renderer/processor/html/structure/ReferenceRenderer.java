package org.openfuxml.renderer.processor.html.structure;

import org.jdom2.Content;
import org.jdom2.Element;
import org.openfuxml.content.ofx.Reference;
import org.openfuxml.renderer.processor.html.interfaces.OfxReferenceRenderer;

public class ReferenceRenderer implements OfxReferenceRenderer
{
	public Content render(Reference reference)
	{
		Element a = new Element("a");
		a.setAttribute("href", reference.getTarget());
		a.setText(reference.getValue());
		return a;
	}
}