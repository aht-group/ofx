package org.openfuxml.renderer.latex.preamble;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class LatexArticle extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexArticle.class);
	
	public LatexArticle()
	{		
		txt.add("\\documentclass[12pt]{article}");
		txt.add("\\usepackage{amsmath}");
		txt.add("\\title{\\LaTeX}");
		txt.add("\\date{}");
	}	
}
