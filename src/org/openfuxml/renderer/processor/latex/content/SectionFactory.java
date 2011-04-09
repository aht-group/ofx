package org.openfuxml.renderer.processor.latex.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.list.List;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.processor.latex.content.list.ListFactory;
import org.openfuxml.renderer.processor.latex.content.table.TableFactory;
import org.openfuxml.renderer.processor.latex.preamble.LatexPreamble;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class SectionFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(SectionFactory.class);
	
	private LatexPreamble latexPreamble;
	int lvl;
	
	public SectionFactory(int lvl, LatexPreamble latexPreamble)
	{
		this.lvl=lvl;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Section section)
	{
		logger.trace("Render section");
		for(Object s : section.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Title){renderTitle((Title)s);}
			else if(s instanceof Section){renderSection((Section)s);}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof Table){renderTable((Table)s);}
			else if(s instanceof List){renderList((List)s);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
//		if(section.getContent()logger.debug(getSectionHeader("x"));
		
	}
	
	private void renderTitle(Title title)
	{
		SectionTitleFactory stf = new SectionTitleFactory(lvl,latexPreamble);
		stf.render(title);
		renderer.add(stf);
	}
	
	private void renderTable(Table table)
	{
		TableFactory f = new TableFactory();
		f.render(table);
		renderer.add(f);
	}
	
	private void renderList(List list)
	{
		ListFactory f = new ListFactory();
		f.render(list);
		renderer.add(f);
	}
	
	private void renderSection(Section section)
	{
		SectionFactory sf = new SectionFactory(lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
}
