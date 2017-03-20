package org.openfuxml.renderer.latex.preamble.packages;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportLatexPackages extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(ReportLatexPackages.class);
	
	public ReportLatexPackages(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render()
	{
		txt.add("");
		txt.add("%% Packages");
		
		
		txt.add("\\setkomafont{sectioning}{\\normalcolor\\bfseries}");
		txt.add("\\typearea[1cm]{10}");
		txt.add("\\setcounter{secnumdepth}{4}");
		
		txt.add("\\usepackage{lipsum}");
		txt.add("\\usepackage{graphicx}");

		
		txt.add("\\usepackage{fancyhdr}");
		txt.add("\\pagestyle{fancy}");
		txt.add("\\fancyhead{}");
	}
}
