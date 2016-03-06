package org.openfuxml.interfaces.renderer;

import java.util.List;

import org.openfuxml.interfaces.io.OfxCharacterWriter;

public interface OfxCharacterRenderer extends OfxCharacterWriter
{
	List<String> getContent();
}