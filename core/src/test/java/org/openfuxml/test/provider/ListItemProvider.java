package org.openfuxml.test.provider;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListItemProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Item description(){return build(li.getWords(1));}
	public static Item build(){return build(null);}
	
	private static Item build(String name)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(10));
		
		Item item = new Item();
		item.setName(name);
		item.getContent().add(p);
		return item;
	}
}