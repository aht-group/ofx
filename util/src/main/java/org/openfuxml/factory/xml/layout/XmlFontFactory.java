package org.openfuxml.factory.xml.layout;

import org.openfuxml.content.layout.Font;

public class XmlFontFactory
{	
	public static Font relative(int value)
	{
		Font xml = new Font();
		xml.setRelativeSize(value);;
		return xml;
	}
}
