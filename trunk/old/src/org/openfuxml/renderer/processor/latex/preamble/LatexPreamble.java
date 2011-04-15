package org.openfuxml.renderer.processor.latex.preamble;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.renderer.processor.latex.preamble.interfaces.SectionHeaderNameFactory;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class LatexPreamble extends AbstractOfxLatexRenderer implements OfxLatexRenderer,SectionHeaderNameFactory
{
	static Log logger = LogFactory.getLog(LatexPreamble.class);
	
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
