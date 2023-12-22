package org.openfuxml.factory.xml.list;

import java.util.List;

import org.openfuxml.model.xml.core.list.Item;
import org.openfuxml.model.xml.core.ofx.Paragraph;

public class XmlListItemFactory
{	
	public static Item build()
	{
		Item xml = new Item();
		return xml;
	}
	
	public static Item build(String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item xml = build();
		xml.getContent().add(p);

		return xml;
	}
	
	public static Item build(String localeCode, String name, String text)
	{
		Paragraph p = new Paragraph();
		p.getContent().add(text);
		
		Item xml = build();
		xml.setLang(localeCode);
		xml.setName(name);
		xml.getContent().add(p);

		return xml;
	}
	
	public static Item build(String localeCode, Paragraph paragraph)
	{
		Item item = XmlListItemFactory.build();
		item.setLang(localeCode);
		item.getContent().add(paragraph);
		return item;
	}
	
	public static Item build(List<Paragraph> paragraphs)
	{
		Item item = XmlListItemFactory.build();
		item.getContent().addAll(paragraphs);
		return item;
	}
}
