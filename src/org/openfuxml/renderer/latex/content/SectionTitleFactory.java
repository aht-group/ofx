package org.openfuxml.renderer.latex.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.latex.document.LatexDocument;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class SectionTitleFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(SectionTitleFactory.class);
	
	int lvl;
	
	public SectionTitleFactory(int lvl, LatexDocument latexDocument)
	{
		this.lvl=lvl;
	}
	
	public void render(Title title)
	{
		logger.trace("Render title");
		logger.warn("Ignoring numbring");
		renderTitle(title.getValue());
	}
	
	public void renderTitle(String header)
	{
		txt.add("\\section{"+lvl+" "+header+"}");
	}
}
