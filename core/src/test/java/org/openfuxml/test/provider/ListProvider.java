package org.openfuxml.test.provider;


import org.openfuxml.content.list.Item;
import org.openfuxml.content.list.List;
import org.openfuxml.content.list.Type;
import org.openfuxml.renderer.markdown.structure.TestMdListRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListProvider extends AbstractElementProvider
{
	final static Logger logger = LoggerFactory.getLogger(TestMdListRenderer.class);

	public static List description(boolean ordering){return build(li.getWords(1), ordering, false);}
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

	private static Item buildItem(String name)
	{
		Item item = new Item();
		item.setName(name);
		item.getContent().add(li.getWords(3));
		return item;
	}
}
