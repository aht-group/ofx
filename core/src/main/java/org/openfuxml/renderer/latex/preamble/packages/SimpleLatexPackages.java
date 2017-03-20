package org.openfuxml.renderer.latex.preamble.packages;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLatexPackages extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(SimpleLatexPackages.class);
	
	public SimpleLatexPackages(ConfigurationProvider cp)
	{
		super(cp);
	}
	
	public void render()
	{
		txt.add("");
		txt.add("%% Packages");
		txt.add("\\usepackage{amsmath}");
		txt.add("\\usepackage{hyperref}");
		txt.add("\\usepackage{array}");
		txt.add("\\usepackage{ifthen}");
		txt.add("\\usepackage{paralist}");
		txt.add("");
	}
}
