package org.openfuxml.interfaces.renderer.text;

import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.renderer.OfxCharacterRenderer;

public interface OfxTextRenderer extends OfxCharacterRenderer
{
	String getSingleLine() throws OfxAuthoringException;
}