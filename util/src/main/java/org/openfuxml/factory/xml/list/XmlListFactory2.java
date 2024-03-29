package org.openfuxml.factory.xml.list;

import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.list.Type;

public class XmlListFactory2
{
	public static enum Ordering {ordered,unordered}
	
	public static List build(Ordering ordering){return build(ordering.toString());}
	
	public static List build(String ordering)
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List xml = new List();
		xml.setType(type);

		return xml;
	}
	
	public static List unordered()
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List xml = new List();
		xml.setType(type);

		return xml;
	}
}
