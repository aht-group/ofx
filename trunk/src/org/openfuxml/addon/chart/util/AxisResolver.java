package org.openfuxml.addon.chart.util;

import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Chart;

public class AxisResolver
{	
	public static synchronized boolean includeNullInAutoRange(Chart ofxChart, String code)
	{
		boolean result = true;
		for(Axis axis : ofxChart.getAxis())
		{
			if(axis.isSetCode() && axis.getCode().equals(code))
			{
				if(axis.isSetAutoRangIncludeNull()){return axis.isAutoRangIncludeNull();}
			}
		}
		return result;
	}
}
