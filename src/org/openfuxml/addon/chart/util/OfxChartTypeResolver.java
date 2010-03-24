package org.openfuxml.addon.chart.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.openfuxml.addon.chart.jaxb.Charttype;

public class OfxChartTypeResolver
{
	static Logger logger = Logger.getLogger(OfxChartTypeResolver.class);
	
	public static enum Type{TimeSeries, TimeBar, Bar};
	
	public synchronized static Type getType(Charttype type)
	{
		if(type.isSetTimeseries()){return Type.TimeSeries;}
		if(type.isSetBar()){return Type.Bar;}
		if(type.isSetTimebar()){return Type.TimeBar;}
		logger.warn("Unknown Charttype");
		JaxbUtil.debug(type);
		return null;
	}
}
