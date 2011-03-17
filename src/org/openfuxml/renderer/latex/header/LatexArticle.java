package org.openfuxml.renderer.latex.header;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LatexArticle
{
	static Log logger = LogFactory.getLog(LatexArticle.class);
	
	private List<String> txt;
	
	public LatexArticle()
	{
		txt = new ArrayList<String>();
		
		txt.add("\\documentclass[12pt]{article}");
		txt.add("\\usepackage{amsmath}");
		txt.add("\\title{\\LaTeX}");
		txt.add("\\date{}");
	}
	
	public List<String> render()
	{
		return txt;
	}
	
}
