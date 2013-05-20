package org.openfuxml.renderer.processor.html.interfaces;

import java.util.List;

import org.jdom2.Content;
import org.openfuxml.content.ofx.Section;

public interface OfxSectionRenderer
{
	public List<Content> render(Section section);
}