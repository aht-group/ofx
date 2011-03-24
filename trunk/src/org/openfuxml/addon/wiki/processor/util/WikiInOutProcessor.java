package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.openfuxml.addon.wiki.data.jaxb.Contents;

public interface WikiInOutProcessor
{
	void setDirectories(File srcDir, File dstDir);
	void process(Contents wikiQueries);
}
