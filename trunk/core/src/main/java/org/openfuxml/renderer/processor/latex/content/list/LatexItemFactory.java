package org.openfuxml.renderer.processor.latex.content.list;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;
import org.openfuxml.xml.content.list.Item;

public class LatexItemFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(LatexItemFactory.class);
	
	public LatexItemFactory()
	{
		
	}
	
	public void render(Item item)
	{	
		preTxt.add("");
		preTxt.add("\\item");
		
		for(Object s : item.getContent())
		{
			if     (s instanceof String){}
			else if(s instanceof Paragraph){paragraphRenderer((Paragraph)s,false);}
			else {logger.warn("No Renderer for Element "+s.getClass().getSimpleName());}
		}
	}
}