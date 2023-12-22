package org.openfuxml.factory.xml.layout;

import org.openfuxml.model.xml.core.layout.Container;
import org.openfuxml.model.xml.core.layout.Font;

public class XmlContainerFactory
{	
	public static Container build()
	{
		Container xml = new Container();
		return xml;
	}
	
	public static Container build(Font font)
	{
		Container xml = build();
		xml.getContent().add(font);
		return xml;
	}
}
