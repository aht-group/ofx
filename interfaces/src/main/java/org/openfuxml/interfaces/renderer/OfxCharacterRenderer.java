package org.openfuxml.interfaces.renderer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public interface OfxCharacterRenderer
{
	List<String> getContent();
	void write(Writer w) throws IOException;
}