package org.openfuxml.addon.chart.factory.chart;

import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Charttype;

public class TimeSeriesChartFactory
{
	private boolean withLegend, cumulateValues, withGaps;
	
	public TimeSeriesChartFactory()
	{
		withLegend = false;
		cumulateValues = false;
		withGaps = false;
	}
	
	
	public Chart build(boolean legend,boolean cumulative)
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
	
	public void setWithLegend(boolean withLegend) {this.withLegend = withLegend;}
	public void setCumulateValues(boolean cumulateValues) {this.cumulateValues = cumulateValues;}
	public void setWithGaps(boolean withGaps) {this.withGaps = withGaps;}
}
