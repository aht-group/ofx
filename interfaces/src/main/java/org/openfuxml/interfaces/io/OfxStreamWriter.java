package org.openfuxml.interfaces.io;

import java.io.IOException;
import java.io.Writer;

public interface OfxStreamWriter
{
	void write(Writer w) throws IOException;
}