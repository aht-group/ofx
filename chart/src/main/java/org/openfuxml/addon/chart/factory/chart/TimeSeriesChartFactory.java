package org.openfuxml.addon.chart.factory.chart;

import org.openfuxml.xml.addon.chart.Chart;
import org.openfuxml.xml.addon.chart.Renderer;

public class TimeSeriesChartFactory
{
	private boolean withLegend, cumulateValues, withGaps;
	
	public TimeSeriesChartFactory()
	{
		withLegend = false;
		cumulateValues = false;
		withGaps = false;
	}
	
	
	public Chart build()
	{
		Chart chart = new Chart();
		chart.setLegend(withLegend);
		chart.setRenderer(buildRenderer());
		
		return chart;
	}
	
	private Renderer buildRenderer()
	{
		Renderer type = new Renderer();
		Renderer.Timeseries tsType = new Renderer.Timeseries();
		tsType.setGap(withGaps);
		tsType.setCumulate(cumulateValues);
		type.setTimeseries(tsType);
		return type;
	}
	
	public void setWithLegend(boolean withLegend) {this.withLegend = withLegend;}
	public void setCumulateValues(boolean cumulateValues) {this.cumulateValues = cumulateValues;}
	public void setWithGaps(boolean withGaps) {this.withGaps = withGaps;}
}
