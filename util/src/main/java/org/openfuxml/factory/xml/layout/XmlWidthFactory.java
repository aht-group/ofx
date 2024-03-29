package org.openfuxml.factory.xml.layout;

import org.openfuxml.model.xml.core.layout.Width;

public class XmlWidthFactory
{
	public static enum Unit {cm,percentage,px}
	
	public static Width size(double value)
	{
		Width xml = new Width();
		xml.setValue(value);
		return xml;
	}
	
	public static Width cm(double value)
	{
		Width xml = new Width();
		xml.setValue(value);
		xml.setUnit(Unit.cm.toString());
		return xml;
	}
	
	public static Width px(int value)
	{
		Width xml = new Width();
		xml.setValue(value);
		xml.setUnit(Unit.px.toString());
		return xml;
	}
	
	public static Width percentage(double value)
	{
		Width xml = new Width();
		xml.setValue(value);
		xml.setUnit(Unit.percentage.toString());
		return xml;
	}
}
