package org.openfuxml.factory.xml.ofx.list;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.xml.content.list.Item;

public class XmlListItemFactory
{	
	public static Item build(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item xml = new Item();
		xml.getContent().add(p);

		return xml;
	}
}
