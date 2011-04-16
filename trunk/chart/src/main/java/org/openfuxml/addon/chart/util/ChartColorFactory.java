package org.openfuxml.addon.chart.util;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.chart.data.jaxb.Chart;

public class ChartColorFactory
{
	static Log logger = LogFactory.getLog(ChartColorFactory.class);
	
	public static enum Area{backgroundChart,backgroundPlot,
							gridRange,gridDomain}
	
	public static synchronized Color createColor(Chart ofxChart, Area area)
	{
		if(ofxChart.isSetColors()  && ofxChart.getColors().isSetColor())
		{
			for(org.openfuxml.addon.chart.data.jaxb.Color c : ofxChart.getColors().getColor())
			{
				if(c.getTyp().equals(area.toString()))
				{
					return new java.awt.Color(c.getR(),c.getB(),c.getG(),c.getA());
				}
			}
		}
		return getDefault(area);
	}
	
	public static synchronized org.openfuxml.addon.chart.data.jaxb.Color create(int r, int g, int b, int a,Area area)
	{
		org.openfuxml.addon.chart.data.jaxb.Color color = create(r, g, b, a);
		color.setTyp(area.toString());
		return color;
	}
	
	public static synchronized org.openfuxml.addon.chart.data.jaxb.Color create(int r, int g, int b, int a)
	{
		org.openfuxml.addon.chart.data.jaxb.Color color = new org.openfuxml.addon.chart.data.jaxb.Color();
		color.setR(r);
		color.setG(g);
		color.setB(b);
		color.setA(a);
		return color;
	}
	
	public static Color create(org.openfuxml.addon.chart.data.jaxb.Color color)
	{
		return new Color(color.getR(), color.getG(), color.getB(), color.getA());
	}
	
	public static synchronized Map<String,java.awt.Color> getColorMap(Chart.Colors colors, String typ)
	{
		Map<String,java.awt.Color> map = new Hashtable<String,java.awt.Color>();
		for(org.openfuxml.addon.chart.data.jaxb.Color color : colors.getColor())
		{
			if(color.getTyp().equals(typ))
			{
				map.put(color.getCode(), create(color));
			}
		}
		return map;
	}
	
	private static synchronized Color getDefault(Area area)
	{
		java.awt.Color result = null;
		switch(area)
		{
			case backgroundChart: 	result=Color.WHITE;break;
			case backgroundPlot: 	result=Color.GRAY;break;
			case gridRange: 		result=Color.LIGHT_GRAY;break;
			case gridDomain: 		result=Color.LIGHT_GRAY;break;
			default: 				result=Color.BLACK;
		}
		return result;
	}
}
