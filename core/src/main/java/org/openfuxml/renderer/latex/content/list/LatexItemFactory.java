package org.openfuxml.renderer.latex.content.list;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.renderer.latex.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.latex.content.list.LatexListFactory.ListType;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.xml.content.list.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LatexItemFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	final static Logger logger = LoggerFactory.getLogger(LatexItemFactory.class);
	
	public LatexItemFactory()
	{
		
	}
	
	public void render(ListType lt, Item item) throws OfxAuthoringException
	{	
		preTxt.add("");
		StringBuffer sb = new StringBuffer();
		sb.append("\\item");
		
		if(lt==LatexListFactory.ListType.description)
		{
			if(!item.isSetName()){throw new OfxAuthoringException("<description.list> needss a item@name");}
			sb.append(" [").append(item.getName()).append("]");
		}
		preTxt.add(sb.toString());
		
		
		for(Object s : item.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,false);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
	}
}