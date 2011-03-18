package org.openfuxml.renderer.latex.preamble;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.latex.preamble.interfaces.SectionHeaderNameFactory;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class LatexPreamble extends AbstractOfxLatexRenderer implements OfxLatexRenderer,SectionHeaderNameFactory
{
	static Log logger = LogFactory.getLog(LatexPreamble.class);
	
	public LatexPreamble()
	{
		
	}
	
	public void render()
	{
		
		LatexArticle article = new LatexArticle();
		renderer.add(article);
	}
	
	public String getSectionHeaderName(int lvl)
	{
		return "sectionsxx";
	}
}
