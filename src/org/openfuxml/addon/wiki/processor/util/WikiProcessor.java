package org.openfuxml.addon.wiki.processor.util;

import java.io.File;

import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.renderer.data.exception.OfxAuthoringException;

public interface WikiProcessor
{
	public static enum WikiFileExtension {txt,xhtml,xml}
	
	void setDirectories(File srcDir, File dstDir);
	void process(Contents wikiQueries) throws OfxWikiException, OfxAuthoringException;
}
