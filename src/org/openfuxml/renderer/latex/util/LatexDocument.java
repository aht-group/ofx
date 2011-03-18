package org.openfuxml.renderer.latex.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.latex.content.SectionFactory;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;

public class LatexDocument extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexDocument.class);
	
	private int lvl;
	private LatexPreamble latexPreamble;
	
	public LatexDocument(LatexPreamble latexPreamble)
	{
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Content content)
	{
		lvl = 0;
		
		preTxt.add("\\begin{document}");
		for(Object s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
		postTxt.add("\\end{document}");
	}
	
	private void renderSection(Section section)
	{
		SectionFactory sf = new SectionFactory(lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
}
