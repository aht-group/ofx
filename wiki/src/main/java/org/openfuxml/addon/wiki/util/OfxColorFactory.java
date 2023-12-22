package org.openfuxml.addon.wiki.util;

import org.openfuxml.model.xml.addon.wiki.Color;
import org.openfuxml.model.xml.addon.wiki.Ofxchart;

public class OfxColorFactory
{
	public static synchronized java.awt.Color createColor(Ofxchart ofxChart, String type, java.awt.Color result)
	{
		for(Color c : ofxChart.getColors().getColor())
		{
			if(c.getTyp().equals(type))
			{
				return new java.awt.Color(c.getR(),c.getB(),c.getG(),c.getA());
			}
		}
		return result;
	}
}
