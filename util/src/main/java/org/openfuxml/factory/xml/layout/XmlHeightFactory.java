package org.openfuxml.factory.xml.layout;

import org.openfuxml.model.xml.core.layout.Height;

public class XmlHeightFactory
{
	public static enum Unit {em,shift,px}
	
	public static Height em(double value)
	{
		Height xml = new Height();
		xml.setValue(value);
		xml.setUnit(Unit.em.toString());
		return xml;
	}
	
	public static Height shift(double value)
	{
		Height xml = new Height();
		xml.setValue(value);
		xml.setUnit(Unit.shift.toString());
		return xml;
	}
	
	public static Height px(int value)
	{
		Height xml = new Height();
		xml.setValue(value);
		xml.setUnit(Unit.px.toString());
		return xml;
	}
	
	public static Height size(double value)
	{
		Height xml = new Height();
		xml.setValue(value);
		return xml;
	}
}
