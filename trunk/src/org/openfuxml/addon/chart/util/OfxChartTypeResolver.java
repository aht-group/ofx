package org.openfuxml.addon.chart.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.chart.jaxb.AxisType;
import org.openfuxml.addon.chart.jaxb.Charttype;

public class OfxChartTypeResolver
{
	static Log logger = LogFactory.getLog(OfxChartTypeResolver.class);
	
	public static enum Type{TimeSeries, TimeBar, Bar, Gantt, Spline};
	
	public static enum AxisOrientation{range,domain};
	public static enum ChartAxisType{Nil,Number,Date};
	
	public synchronized static Type getType(Charttype type)
	{
		if(type.isSetTimeseries()){return Type.TimeSeries;}
		if(type.isSetBar()){return Type.Bar;}
		if(type.isSetTimebar()){return Type.TimeBar;}
		if(type.isSetGantt()){return Type.Gantt;}
		if(type.isSetSpline()){return Type.Spline;}
		logger.warn("Unknown Charttype");
		JaxbUtil.debug(type);
		return null;
	}
	
	public synchronized static ChartAxisType getAxisType(AxisType type)
	{
		if(type!=null)
		{
			if(type.isSetNumber()){return ChartAxisType.Number;}
			if(type.isSetDate()){return ChartAxisType.Date;}
		}
		return ChartAxisType.Nil;
	}
}
