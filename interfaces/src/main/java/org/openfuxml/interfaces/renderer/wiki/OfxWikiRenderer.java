package org.openfuxml.interfaces.renderer.wiki;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface OfxWikiRenderer
{
	List<String> getContent();
	void write(Writer w) throws IOException;
}
