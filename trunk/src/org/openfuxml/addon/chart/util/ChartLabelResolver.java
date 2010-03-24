package org.openfuxml.addon.chart.util;

import org.openfuxml.addon.chart.jaxb.Chart;

public class ChartLabelResolver
{	
	public synchronized static String getTitle(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetTitle()){result = ofxChart.getTitle().getLabel();}
		return result;
	}
	
	public synchronized static String getXaxis(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetXAxis()){result = ofxChart.getXAxis().getLabel();}
		return result;
	}
	
	public synchronized static String getYaxis(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetYAxis()){result = ofxChart.getYAxis().getLabel();}
		return result;
	}
}
