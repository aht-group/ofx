package org.openfuxml.renderer.processor.latex.content;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionTitleFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(SectionTitleFactory.class);
	
	private LatexPreamble latexPreamble;
	int lvl;
	
	public SectionTitleFactory(int lvl, LatexPreamble latexPreamble)
	{
		this.latexPreamble=latexPreamble;
		this.lvl=lvl;
	}
	
	public void render(Title title)
	{
		logger.trace("Render title");
		if(title.isSetNumbering()){logger.warn("Ignoring numbring");}
		
		txt.add("");
		StringBuffer sb = new StringBuffer();
		sb.append("\\");
		sb.append(latexPreamble.getSectionHeaderName(lvl)).append("{");
		sb.append(title.getValue());
		sb.append("}");
		txt.add(sb.toString());
	}
}