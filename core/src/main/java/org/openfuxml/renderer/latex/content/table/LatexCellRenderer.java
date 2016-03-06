package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.layout.Font;
import org.openfuxml.content.list.List;
import org.openfuxml.content.media.Image;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.table.Cell;
import org.openfuxml.content.text.Emphasis;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.text.OfxEmphasisFactory;
import org.openfuxml.interfaces.configuration.DefaultSettingsManager;
import org.openfuxml.interfaces.media.CrossMediaManager;
import org.openfuxml.interfaces.renderer.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.util.LatexFontUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCellRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexCellRenderer.class);
	
	private Emphasis emphasisOverride; public void setEmphasisOverride(Emphasis emphasisOverride) {this.emphasisOverride = emphasisOverride;}
	private Font font; public void setFont(Font font) {this.font = font;}

	public LatexCellRenderer(CrossMediaManager cmm,DefaultSettingsManager dsm)
	{
		super(cmm,dsm);
	}
	
	public void render(Cell cell) throws OfxAuthoringException
	{	
		// Font has special handling!
		for(Object o : cell.getContent())
		{
			if(o instanceof Font)
			{
				if(font!=null){throw new OfxAuthoringException("More than one "+Font.class.getSimpleName()+" in "+Paragraph.class.getSimpleName()+" not allowed");}
				font = (Font)o;
			}
		}
				
		if(font!=null){preTxt.add(LatexFontUtil.environmentBegin(font));}
		
		for(Object s : cell.getContent())
		{
			logger.trace(s.getClass().getName());
			if     (s instanceof String){}
			else if(s instanceof Paragraph)
			{
				Paragraph p = applyEmphasis((Paragraph)s);
				paragraphRenderer(p,true);
			}
			else if(s instanceof Image){renderImage((Image)s);}
			else if(s instanceof List){renderList((List)s,this);}
			else if(s instanceof Font){}
			else {logger.warn("No Renderer for "+s.getClass().getSimpleName());}
		}
		
//		postTxt.add("");
		if(font!=null){postTxt.add(LatexFontUtil.environmentEnd());}
	}
	
	private Paragraph applyEmphasis(Paragraph p) throws OfxAuthoringException
	{
		if(emphasisOverride==null)
		{
			return p;
		}
		else
		{
			Paragraph pWithEmphasis = new Paragraph();
			OfxEmphasisFactory eF = new OfxEmphasisFactory(emphasisOverride);
			for(Object o : p.getContent())
			{
				if(o==null){throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
				else if(o instanceof String){pWithEmphasis.getContent().add(eF.build((String)o));}
				else {throw new OfxAuthoringException(Paragraph.class.getSimpleName()+" has no content");}
			}
			return pWithEmphasis;
		}
	}
}