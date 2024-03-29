package org.openfuxml.factory.xml.ofx.layout;

import org.openfuxml.model.xml.core.layout.Line;

public class XmlLineFactory
{
	public static enum Orientation{top,bottom,left,right};
	
	public static Line top()
	{
		Line xml = new Line();
		xml.setOrientation(Orientation.top.toString());
		return xml;
	}
	
	public static Line bottom()
	{
		Line xml = new Line();
		xml.setOrientation(Orientation.bottom.toString());
		return xml;
	}
}
