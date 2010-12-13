package org.openfuxml.addon.chart.util;

import org.apache.log4j.Logger;
import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.AxisResolver.AxisType;

public class ChartLabelResolver
{	
	static Logger logger = Logger.getLogger(ChartLabelResolver.class);
	
	public synchronized static String getTitle(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetTitle()){result = ofxChart.getTitle().getLabel();}
		return result;
	}
	
	public synchronized static String getAxisLabelX(Chart ofxChart){return getAxisLabel(ofxChart, AxisType.domain);}
	public synchronized static String getAxisLabelY(Chart ofxChart){return getAxisLabel(ofxChart, AxisType.range);}
	
	public synchronized static String getAxisLabel(Chart ofxChart, AxisType type)
	{
		String result = null;
		if(ofxChart!=null)
		{
			for(Axis axis : ofxChart.getAxis())
			{
				if(axis.isSetCode() && axis.getCode().equals(type.toString()) && axis.isSetLabel() && axis.getLabel().isSetText())
				{
					result = axis.getLabel().getText();
					break;
				}
			}
		}
		return result;
	}
}
