package org.openfuxml.renderer.processor.latex.content.list;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.list.Item;
import org.openfuxml.content.ofx.list.List;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class ListFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(ListFactory.class);
	
	public ListFactory()
	{

	}
	
	public void render(List list)
	{	
		preTxt.add("");
		preTxt.add("\\begin{itemize}");
		
		for(Item item : list.getItem())
		{
			ItemFactory f = new ItemFactory();
			f.render(item);
			renderer.add(f);
		}
		
		postTxt.add("\\end{itemize}");
	}
}
