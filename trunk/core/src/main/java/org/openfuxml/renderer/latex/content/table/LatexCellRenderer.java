package org.openfuxml.renderer.latex.content.table;

import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Emphasis;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.content.OfxEmphasisFactory;
import org.openfuxml.interfaces.OfxLatexRenderer;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexCellRenderer extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexCellRenderer.class);
	
	private Emphasis emphasisOverride;
	
	public LatexCellRenderer(){this(null);}
	public LatexCellRenderer(Emphasis emphasis)
	{
		this.emphasisOverride=emphasis;
	}
	
	public void render(Cell cell) throws OfxAuthoringException
	{	
		for(Object s : cell.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph)
			{
				Paragraph p = applyEmphasis((Paragraph)s);
				paragraphRenderer(p,true);
			}
			else if(s instanceof List){renderList((List)s,this);}
			else {logger.warn("No Renderer for "+s.getClass().getSimpleName());}
		}
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