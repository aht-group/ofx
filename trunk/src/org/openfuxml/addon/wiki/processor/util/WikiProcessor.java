package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Contents;

public interface WikiProcessor
{
	public static enum WikiFileExtension {txt,xhtml}
	
	void setDirectories(File srcDir, File dstDir);
	void process(Contents wikiQueries) throws OfxWikiException;
}
