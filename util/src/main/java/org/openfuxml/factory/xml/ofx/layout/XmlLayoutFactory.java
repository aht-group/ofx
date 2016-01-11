package org.openfuxml.factory.xml.ofx.layout;

import org.openfuxml.content.layout.Font;
import org.openfuxml.content.layout.Layout;
import org.openfuxml.content.layout.Spacing;

public class XmlLayoutFactory
{
	public static Layout build()
	{
		Layout xml = new Layout();
		return xml;
	}
	
	public static Layout build(Font font)
	{
		Layout xml = build();
		xml.setFont(font);
		return xml;
	}
	
	public static Layout build(Spacing spacing)
	{
		Layout xml = build();
		xml.setSpacing(spacing);
		return xml;
	}
}
