package org.openfuxml.interfaces.renderer.text;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.openfuxml.exception.OfxAuthoringException;

public interface OfxTextRenderer
{
	String getSingleLine() throws OfxAuthoringException;
	List<String> getContent();
	void write(Writer w) throws IOException;
}
