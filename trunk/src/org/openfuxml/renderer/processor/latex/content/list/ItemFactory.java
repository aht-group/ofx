package org.openfuxml.renderer.processor.latex.content.list;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.ofx.list.Item;
import org.openfuxml.content.ofx.list.List;
import org.openfuxml.content.ofx.table.Table;
import org.openfuxml.renderer.processor.latex.util.AbstractOfxLatexRenderer;
import org.openfuxml.renderer.processor.latex.util.OfxLatexRenderer;

public class ItemFactory extends AbstractOfxLatexRenderer implements OfxLatexRenderer
{
	static Log logger = LogFactory.getLog(ItemFactory.class);
	
	public ItemFactory()
	{
		
	}
	
	public void render(Item item)
	{	
		preTxt.add("\\item");
		
		
		txt.add("Item");
	}
}
