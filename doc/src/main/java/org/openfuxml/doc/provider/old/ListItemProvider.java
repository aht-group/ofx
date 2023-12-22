package org.openfuxml.doc.provider.old;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.model.xml.core.list.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListItemProvider
{	
	final static Logger logger = LoggerFactory.getLogger(ListItemProvider.class);
	
	public static Item description(){return build(DemoContentProvider.li.getWords(1));}
	public static Item build(){return build(null);}
	
	private static Item build(String name)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(DemoContentProvider.li.getWords(10));
		
		Item item = new Item();
		item.setName(name);
		item.getContent().add(p);
		return item;
	}
}