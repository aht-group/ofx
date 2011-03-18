package org.openfuxml.renderer.latex.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.latex.content.SectionFactory;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class LatexDocument extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexDocument.class);
	
	private int lvl;
	
	
	public LatexDocument()
	{

	}
	
	public void render(Content content)
	{
		lvl = 0;
		
		preTxt.add("\\begin{document}");
		for(Serializable s : content.getContent())
		{
			if(s instanceof Section){renderSection((Section)s);}
		}
		postTxt.add("\\end{document}");
	}
	
	private void renderSection(Section section)
	{
		SectionFactory sf = new SectionFactory(lvl+1,this);
		sf.render(section);
		renderer.add(sf);
	}
}
