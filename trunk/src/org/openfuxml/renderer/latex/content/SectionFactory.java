package org.openfuxml.renderer.latex.content;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.renderer.latex.document.LatexDocument;
import org.openfuxml.renderer.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.OfxLatexRenderer;

public class SectionFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(SectionFactory.class);
	
	int lvl;
	
	private LatexDocument latexDocument;
	
	public SectionFactory(int lvl, LatexDocument latexDocument)
	{
		this.lvl=lvl;
		this.latexDocument=latexDocument;
	}
	
	public void render(Section section)
	{
		logger.trace("Render section");
		for(Serializable s : section.getContent())
		{
			if(s instanceof Title){renderTitle((Title)s);}
			if(s instanceof Section){renderSection((Section)s);}
		}
//		if(section.getContent()logger.debug(getSectionHeader("x"));
		
	}
	
	private void renderTitle(Title title)
	{
		SectionTitleFactory stf = new SectionTitleFactory(lvl,latexDocument);
		stf.render(title);
		renderer.add(stf);
	}
	
	private void renderSection(Section section)
	{
		SectionFactory sf = new SectionFactory(lvl+1,latexDocument);
		sf.render(section);
		renderer.add(sf);
	}
}
