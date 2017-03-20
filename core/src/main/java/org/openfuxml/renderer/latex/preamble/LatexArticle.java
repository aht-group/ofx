package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.LatexSectionHeaderNameFactory;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.renderer.latex.preamble.packages.SimpleLatexPackages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexArticle extends AbstractLatexDocument implements OfxLatexRenderer,LatexSectionHeaderNameFactory
{
	final static Logger logger = LoggerFactory.getLogger(LatexArticle.class);
	
	public LatexArticle(ConfigurationProvider cp)
	{
		super(cp);
		txt.add("\\documentclass[12pt]{article}");
		
		SimpleLatexPackages renderPackages = new SimpleLatexPackages(cp);
		renderer.add(renderPackages);		
		
		txt.add("\\title{\\LaTeX}");
		txt.add("\\date{}");
	}
	
	@Override public String getSectionHeaderName(int lvl){return super.getDefaultSectionHeaderName(lvl);}
}