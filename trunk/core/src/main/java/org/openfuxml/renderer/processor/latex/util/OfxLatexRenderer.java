package org.openfuxml.renderer.processor.latex.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface OfxLatexRenderer
{
	List<String> getContent();
	void write(Writer w) throws IOException;
}
