package org.openfuxml.addon.chart.factory.chart;

import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.addon.chart.data.jaxb.Charttype;

public class TimeSeriesChartFactory
{
	public static Chart build(boolean legend,boolean cumulative)
	{
		Chart chart = new Chart();
		chart.setLegend(true);
		chart.setCharttype(getType(cumulative));
		
		return chart;
	}
	
	private static Charttype getType(boolean cumulative)
	{
		Charttype type = new Charttype();
		Charttype.Timeseries tsType = new Charttype.Timeseries();
		tsType.setGap(true);
		tsType.setCumulate(cumulative);
		type.setTimeseries(tsType);
		return type;
	}
}
