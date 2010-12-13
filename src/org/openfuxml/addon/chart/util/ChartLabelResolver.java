package org.openfuxml.addon.chart.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.renderer.gantt.GanttChartRenderer;

public class ChartLabelResolver
{	
	static Logger logger = Logger.getLogger(ChartLabelResolver.class);
	
	public synchronized static String getTitle(Chart ofxChart)
	{
		String result = null;
		if(ofxChart.isSetTitle()){result = ofxChart.getTitle().getLabel();}
		return result;
	}
	
	public synchronized static String getAxisLabelX(Chart ofxChart){return getAxisLabel(ofxChart, "x");}
	public synchronized static String getAxisLabelY(Chart ofxChart){return getAxisLabel(ofxChart, "y");}
	
	public synchronized static String getAxisLabel(Chart ofxChart, String code)
	{
		String result = null;
		if(ofxChart!=null)
		{
			for(Axis axis : ofxChart.getAxis())
			{
				if(axis.isSetCode() && axis.getCode().equals(code) && axis.isSetLabel())
				{
					result = axis.getLabel();
				}
			}
		}
		return result;
	}
}
