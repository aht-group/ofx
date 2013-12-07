package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.preamble.LatexPreamble;
import org.openfuxml.xml.content.list.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexSectionRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	private LatexPreamble latexPreamble;
	int lvl;
	
	public LatexSectionRenderer(int lvl, LatexPreamble latexPreamble)
	{
		this.lvl=lvl;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Section section) throws OfxAuthoringException
	{
		logger.trace("Render section");
		for(Object s : section.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Title){renderTitle(section,(Title)s);}
			else if(s instanceof Section){renderSection((Section)s);}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof Table){renderTable((Table)s);}
			else if(s instanceof List){renderList((List)s,this);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
//		if(section.getContent()logger.debug(getSectionHeader("x"));
		
	}
	
	private void renderTitle(Section section,Title title)
	{
		LatexSectionTitleRenderer stf = new LatexSectionTitleRenderer(lvl,latexPreamble);
		stf.render(section,title);
		renderer.add(stf);
	}
	
	private void renderTable(Table table) throws OfxAuthoringException
	{
		LatexTableRenderer f = new LatexTableRenderer();
		f.render(table);
		renderer.add(f);
	}
	
	private void renderSection(Section section) throws OfxAuthoringException
	{
		LatexSectionRenderer sf = new LatexSectionRenderer(lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
}
