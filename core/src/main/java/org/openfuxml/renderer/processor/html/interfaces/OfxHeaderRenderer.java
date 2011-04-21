package org.openfuxml.renderer.processor.html.interfaces;

import org.jdom.Content;
import org.openfuxml.content.ofx.Section;

public interface OfxHeaderRenderer
{
	public Content render(Section actualSection);
}