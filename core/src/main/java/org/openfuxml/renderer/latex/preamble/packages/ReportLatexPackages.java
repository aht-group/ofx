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

		txt.add("");
		txt.add("\\usepackage{float}");
		
		txt.add("");
		txt.add("%% Tables");
		txt.add("\\usepackage{tabu}");
		txt.add("\\usepackage{tabularx}");
		txt.add("\\usepackage{longtable}");
		txt.add("\\usepackage{booktabs}");
		txt.add("\\usepackage{multicol}");
		txt.add("\\usepackage{multirow}");
		
		txt.add("");
		txt.add("%% Lists");
		txt.add("\\usepackage{enumitem}");
		
		txt.add("");
		txt.add("%% Header");
		txt.add("\\usepackage{fancyhdr}");
		txt.add("\\pagestyle{fancy}");
		txt.add("\\fancyhead{}");
	}
}