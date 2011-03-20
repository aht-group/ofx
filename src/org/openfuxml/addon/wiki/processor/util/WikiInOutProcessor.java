package org.openfuxml.addon.wiki.processor.util;

import java.io.File;
import java.util.List;

import org.openfuxml.addon.wiki.data.jaxb.Content;

public interface WikiInOutProcessor
{
	void setDirectories(File srcDir, File dstDir);
	void process(List<Content> lContent);
}
