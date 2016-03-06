package org.openfuxml.interfaces.renderer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.w3c.dom.Element;

public interface OfxXmlRenderer
{
	List<Element> getContent();
	void write(Writer w) throws IOException;
}