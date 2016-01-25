package org.openfuxml.interfaces.renderer.markdown;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface OfxMarkdownRenderer
{
	List<String> getContent();
	void write(Writer w) throws IOException;
}
