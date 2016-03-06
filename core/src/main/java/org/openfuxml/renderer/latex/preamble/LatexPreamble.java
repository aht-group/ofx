package org.openfuxml.renderer.latex.preamble;

import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.factory.LatexSectionHeaderNameFactory;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexPreamble extends AbstractOfxLatexRenderer implements OfxLatexRenderer,LatexSectionHeaderNameFactory
{
	final static Logger logger = LoggerFactory.getLogger(LatexPreamble.class);
	
	private LatexSectionHeaderNameFactory shnf;
	private LatexArticle article;
	
	public LatexPreamble(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
		article = new LatexArticle(cmm,dsm);
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
