package org.openfuxml.util.provider;

import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.factory.xml.list.XmlListFactory2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListProvider
{
	final static Logger logger = LoggerFactory.getLogger(ListProvider.class);

	public static List build(boolean ordering){return build(DemoContentProvider.li.getWords(1), ordering, false);}
	public static List build(boolean ordering, boolean nested){return build(null, ordering, nested);}

	private static List build(String name, boolean ordering, boolean nested)
	{
		List l = new List();
		Type t = new Type();

		if(ordering){t.setOrdering("ordered");}
		else {t.setOrdering("unordered");}

		l.setType(t);
		l.getItem().add(buildItem(name));
		l.getItem().add(buildItem(name));
		l.getItem().add(buildItem(name));
		l.getItem().add(buildItem(name));

		return l;
	}

	public static List buildDescription()
	{
		List l = new List();
		Type t = new Type();t.setDescription(true);
		l.setType(t);
		l.getItem().add(buildItem(DemoContentProvider.li.getWords(1)));
		l.getItem().add(buildItem(DemoContentProvider.li.getWords(1,1)));
		l.getItem().add(buildItem(DemoContentProvider.li.getWords(1,2)));
		l.getItem().add(buildItem(DemoContentProvider.li.getWords(1,3)));

		return l;
	}

	private static Item buildItem(String name)
	{
		Item item = new Item();
		item.setName(name);
		item.getContent().add(DemoContentProvider.li.getWords(3));
		return item;
	}
	
	public static List unordered()
	{
		List xml = XmlListFactory2.unordered();
		xml.getItem().add(buildItem(null));
		xml.getItem().add(buildItem(null));
		xml.getItem().add(buildItem(null));
		return xml;
	}
}