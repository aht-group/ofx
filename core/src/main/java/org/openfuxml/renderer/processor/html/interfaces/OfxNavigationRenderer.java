package org.openfuxml.renderer.processor.html.interfaces;

import org.jdom2.Element;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.content.ofx.Section;

public interface OfxNavigationRenderer
{
	public Element render(Ofxdoc ofxDoc, Section actualSection);
}