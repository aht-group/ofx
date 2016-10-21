package org.openfuxml.factory.xml.ofx.list;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;

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
}
