package org.openfuxml.factory.xml.list;

import java.util.List;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.ofx.Paragraph;

public class OfxListItemFactory
{
	public static Item build(List<Paragraph> paragraphs)
	{
		Item item = new Item();
		item.getContent().addAll(paragraphs);
		return item;
	}
}