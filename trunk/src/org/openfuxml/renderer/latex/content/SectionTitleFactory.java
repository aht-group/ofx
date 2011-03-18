package org.openfuxml.renderer.latex.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class SectionTitleFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(SectionTitleFactory.class);
	
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
		logger.warn("Ignoring numbring");
		
		StringBuffer sb = new StringBuffer();
		sb.append("\\");
		sb.append(latexPreamble.getSectionHeaderName(lvl)).append("{");
		sb.append(title.getValue());
		sb.append("}");
		txt.add(sb.toString());
	}
}
