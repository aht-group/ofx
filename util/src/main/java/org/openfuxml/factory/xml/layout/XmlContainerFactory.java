package org.openfuxml.factory.xml.layout;

import org.openfuxml.content.layout.Container;
import org.openfuxml.content.layout.Font;

public class XmlContainerFactory
{	
	public static Container build(Font font)
	{
		Container xml = new Container();
		xml.getContent().add(font);
		return xml;
	}
}
