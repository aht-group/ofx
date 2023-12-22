package org.openfuxml.renderer.latex.content.structure;

import java.util.Objects;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Highlight;
import org.openfuxml.content.ofx.Include;
import org.openfuxml.content.ofx.Listing;
import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.interfaces.renderer.latex.LatexSectionHeaderNameFactory;
import org.openfuxml.interfaces.renderer.latex.OfxLatexRenderer;
import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.media.Image;
import org.openfuxml.model.xml.core.table.Table;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.listing.LatexListingRenderer;
import org.openfuxml.renderer.latex.content.table.LatexTableRenderer;
import org.openfuxml.renderer.latex.content.text.LatexCommentRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JaxbUtil;

public class LatexSectionRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexSectionRenderer.class);
	
	private LatexSectionHeaderNameFactory latexPreamble;
	int lvl;
	
	public LatexSectionRenderer(ConfigurationProvider cp, int lvl, LatexSectionHeaderNameFactory latexPreamble)
	{
		super(cp);
		this.lvl=lvl;
		this.latexPreamble=latexPreamble;
	}
	
	public void render(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		if(Objects.isNull(section.isContainer())){section.setContainer(false);}
		if(section.isContainer()){lvl=lvl-1;}
		
		preTxt.addAll(LatexCommentRenderer.stars());
		if(section.isContainer() && Objects.nonNull(section.getInclude()))
		{
			if(section.getContent().size()>0)
			{
				preTxt.addAll(LatexCommentRenderer.comment("All content of "+Section.class.getSimpleName()+" will be ignored because of inlcude"));
			}
			LatexIncludeRenderer rComment = new LatexIncludeRenderer(cp);
			rComment.render(Section.class,section.getInclude(),true);
			renderer.add(rComment);
			return;
		}

		preTxt.addAll(LatexCommentRenderer.comment("Rendering a "+Section.class.getSimpleName()+" with: "+this.getClass().getSimpleName()));
		
		logger.trace("Render section");
		
		// Comment always on top!
		for(Object o : section.getContent())
		{
			if (o instanceof Comment)
			{
				LatexCommentRenderer rComment = new LatexCommentRenderer(cp);
				rComment.render((Comment)o);
				renderer.add(rComment);
			}
		}
		
		// Title after comment!
		for(Object s : section.getContent())
		{
			if(s instanceof Title){renderTitle(section,(Title)s);}
		}
		
		
		for(Object s : section.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Section){renderSection((Section)s);}
			else if(s instanceof Paragraph)
			{
				try{paragraphRenderer((Paragraph)s,true);}
				catch(OfxAuthoringException e) {JaxbUtil.info(section);throw e;}
			}
			else if(s instanceof Highlight){highlightRenderer((Highlight)s);}
			else if(s instanceof Table){renderTable((Table)s);}
			else if(s instanceof Marginalia){renderMarginalia((Marginalia)s);}
			else if(s instanceof List){renderList((List)s,this);}
            else if(s instanceof Listing){renderListing((Listing)s);}
            else if(s instanceof Include){renderInclude((Include)s);}
            else if(s instanceof Image){renderImage((Image)s);}
            else if(s instanceof Comment){}
            else if(s instanceof Title){}
            else if(s==null) {logger.warn("We have nukll"); JaxbUtil.info(section);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
	}
	
	private void renderTitle(Section section,Title title)
	{
		if(!section.isContainer())
		{
			LatexTitleRenderer stf = new LatexTitleRenderer(cp);
			stf.render(title,section,lvl,latexPreamble);
			renderer.add(stf);
		}
	}
	
	private void renderTable(Table table) throws OfxAuthoringException
	{
		LatexTableRenderer f = new LatexTableRenderer(cp);
		f.render(table);
		renderer.add(f);
	}
			
	private void renderSection(Section section) throws OfxAuthoringException, OfxConfigurationException
	{
		LatexSectionRenderer sf = new LatexSectionRenderer(cp,lvl+1,latexPreamble);
		sf.render(section);
		renderer.add(sf);
	}
	
	private void renderInclude(Include include) throws OfxAuthoringException
	{
		LatexIncludeRenderer ir = new LatexIncludeRenderer(cp);
		ir.render(include);
		renderer.add(ir);
	}

    private void renderListing(Listing listing) throws OfxAuthoringException, OfxConfigurationException
    {
        LatexListingRenderer r = new LatexListingRenderer(cp);
        r.render(listing);
        renderer.add(r);
    }
}
