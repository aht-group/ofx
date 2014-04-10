package org.openfuxml.renderer.latex.content.structure;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.listing.LatexListingRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
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
		preTxt.addAll(LatexCommentRenderer.stars());
		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Section.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		for(Object s : section.getContent())
		{
			if (s instanceof Comment)
			{
				LatexCommentRenderer rComment = new LatexCommentRenderer();
				rComment.render((Comment)s);
				renderer.add(rComment);
			}
		}
		
		logger.trace("Render section");
		
		if(!section.isSetTransparent()){section.setTransparent(false);}
		if(section.isTransparent()){lvl=lvl-1;}
		
		for(Object s : section.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Title){renderTitle(section,(Title)s);}
			else if(s instanceof Section){renderSection((Section)s);}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,true);}
			else if(s instanceof Table){renderTable((Table)s);}
			else if(s instanceof List){renderList((List)s,this);}
            else if(s instanceof Listing){renderListing((Listing)s);}
            else if(s instanceof Comment){}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
//		if(section.getContent()logger.debug(getSectionHeader("x"));
		
	}
	
	private void renderTitle(Section section,Title title)
	{
		if(!section.isTransparent())
		{
			LatexSectionTitleRenderer stf = new LatexSectionTitleRenderer(lvl,latexPreamble);
			stf.render(section,title);
			renderer.add(stf);
		}
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

    private void renderListing(Listing listing) throws OfxAuthoringException
    {
        LatexListingRenderer r = new LatexListingRenderer();
        r.render(listing);
        renderer.add(r);
    }
}
