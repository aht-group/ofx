package org.openfuxml.factory.xml.layout;

import org.openfuxml.content.layout.Alignment;

public class XmlAlignmentFactory
{
	public static enum Horizontal {center}
	
	public static Alignment buildHorizontal(Horizontal horizontal)
	{
		Alignment xml = new Alignment();
		xml.setHorizontal(horizontal.toString());
		return xml;
	}
}
