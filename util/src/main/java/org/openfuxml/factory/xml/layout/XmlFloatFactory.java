package org.openfuxml.factory.xml.layout;


public class XmlFloatFactory
{
	public static enum Horizontal {center}
	
	public static org.openfuxml.model.xml.core.layout.Float build(boolean floating)
	{
		org.openfuxml.model.xml.core.layout.Float xml = new org.openfuxml.model.xml.core.layout.Float();
		xml.setValue(floating);
		return xml;
	}
}
