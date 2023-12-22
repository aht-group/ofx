package org.openfuxml.factory.xml.ofx.layout;

import org.openfuxml.model.xml.core.layout.Font;
import org.openfuxml.model.xml.core.layout.Layout;
import org.openfuxml.model.xml.core.layout.Spacing;

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
