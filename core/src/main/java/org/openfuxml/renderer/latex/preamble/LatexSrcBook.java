package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSrcBook extends AbstractLatexDocument implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSrcBook.class);
	
	public LatexSrcBook(ConfigurationProvider cp)
	{
		super(cp);
		txt.add("\\documentclass[");
		txt.add("   10pt,");
		txt.add("   smallheadings,");
		txt.add("   headsepline,");
		txt.add("   pointlessnumbers,");
		txt.add("   bibtotocnumbered,");
		txt.add("   openany");
		txt.add("]{scrbook}");
	}	
}