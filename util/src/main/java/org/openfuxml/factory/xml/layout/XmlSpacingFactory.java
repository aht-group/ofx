package org.openfuxml.factory.xml.layout;

import org.openfuxml.model.xml.core.layout.Spacing;

public class XmlSpacingFactory
{	
	public static Spacing pt(double value)
	{
		Spacing xml = new Spacing();
		xml.setValue(value);
		xml.setUnit(XmlUnitFactory.Unit.pt.toString());
		return xml;
	}
}
