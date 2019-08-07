package org.openfuxml.factory.xml.list;

import java.util.List;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;

public class OfxListItemFactory
{
	public static Item build()
	{
		Item item = new Item();
		return item;
	}
	
	public static Item build(String localeCode, Paragraph paragraph)
	{
		Item item = build();
		item.setLang(localeCode);
		item.getContent().add(paragraph);
		return item;
	}
	public static Item build(List<Paragraph> paragraphs)
	{
		Item item = build();
		item.getContent().addAll(paragraphs);
		return item;
	}
}