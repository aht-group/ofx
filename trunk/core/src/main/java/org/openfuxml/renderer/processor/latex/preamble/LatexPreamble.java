package org.openfuxml.renderer.processor.latex.preamble;

import org.openfuxml.renderer.processor.latex.preamble.interfaces.SectionHeaderNameFactory;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexPreamble extends AbstractOfxLatexRenderer implements OfxLatexRenderer,SectionHeaderNameFactory
{
	final static Logger logger = LoggerFactory.getLogger(LatexPreamble.class);
	
	private SectionHeaderNameFactory shnf;
	private LatexArticle article;
	
	public LatexPreamble()
	{
		article = new LatexArticle();
		shnf = article;
	}
	
	public void render()
	{
		
		renderer.add(article);
	}
	
	public String getSectionHeaderName(int lvl)
	{
		return shnf.getSectionHeaderName(lvl);
	}
}
