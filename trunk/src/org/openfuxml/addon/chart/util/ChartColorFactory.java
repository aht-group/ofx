package org.openfuxml.addon.chart.util;

import java.awt.Color;

import org.openfuxml.addon.chart.jaxb.Chart;

public class ChartColorFactory
{
	public static enum Area{backgroundChart,backgroundPlot,
							gridRange,gridDomain}
	
	public static synchronized Color createColor(Chart ofxChart, Area area)
	{
		if(ofxChart.isSetColors()  && ofxChart.getColors().isSetColor())
		{
			for(org.openfuxml.addon.chart.jaxb.Color c : ofxChart.getColors().getColor())
			{
				if(c.getTyp().equals(area.toString()))
				{
					return new java.awt.Color(c.getR(),c.getB(),c.getG(),c.getA());
				}
			}
		}
		return getDefault(area);
	}
	
	public static synchronized org.openfuxml.addon.chart.jaxb.Color create(int r, int g, int b, int a,Area area)
	{
		org.openfuxml.addon.chart.jaxb.Color color = new org.openfuxml.addon.chart.jaxb.Color();
		color.setTyp(area.toString());
		color.setR(r);
		color.setG(g);
		color.setB(b);
		color.setA(a);
		return color;
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
