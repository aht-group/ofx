package org.openfuxml.interfaces.renderer;

import org.openfuxml.exception.OfxAuthoringException;

public interface OfxTextRenderer extends OfxCharacterRenderer
{
	String getSingleLine() throws OfxAuthoringException;
}