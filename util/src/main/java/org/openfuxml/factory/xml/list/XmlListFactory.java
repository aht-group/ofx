package org.openfuxml.factory.xml.list;

import org.openfuxml.model.xml.core.list.List;
import org.openfuxml.model.xml.core.list.Type;

public class XmlListFactory
{
	public static List unordered()
	{
		Type type = new Type();
		type.setOrdering("unordered");
		
		List list = new List();
		list.setType(type);
		
		return list;
	}
	
	public static List description()
	{
		Type type = new Type();
		type.setDescription(true);
		
		List list = new List();
		list.setType(type);
		
		return list;
	}
}