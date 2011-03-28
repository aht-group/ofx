package org.openfuxml.renderer.processor.latex.preamble;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.processor.latex.preamble.interfaces.SectionHeaderNameFactory;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class LatexArticle extends AbstractOfxLatexRenderer implements OfxLatexRenderer,SectionHeaderNameFactory
{
	static Log logger = LogFactory.getLog(LatexArticle.class);
	
	public LatexArticle()
	{		
		txt.add("\\documentclass[12pt]{article}");
		addPackages();		
		txt.add("\\title{\\LaTeX}");
		txt.add("\\date{}");
	}
	
	private void addPackages()
	{
		txt.add("\\usepackage{amsmath}");
		txt.add("\\usepackage{hyperref}");
	}
	
	// http://en.wikibooks.org/wiki/LaTeX/Document_Structure
	public String getSectionHeaderName(int lvl)
	{
		if      (lvl==1){return "section";}
		else if (lvl==2){return "subsection";}
		else if (lvl==3){return "subsubsection";}
		else if (lvl==4){return "paragraph";}
		else if (lvl==5){return "subparagraph";}
		
		logger.warn("Level "+lvl+" not supported by "+LatexArticle.class.getSimpleName());
		return "section";
	}
}
