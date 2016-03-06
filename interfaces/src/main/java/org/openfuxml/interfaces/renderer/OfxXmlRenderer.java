package org.openfuxml.interfaces.renderer;

import java.util.List;

import org.jdom2.Element;
import org.openfuxml.interfaces.io.OfxStreamWriter;

public interface OfxXmlRenderer extends OfxStreamWriter
{
	List<Element> getContent();
}