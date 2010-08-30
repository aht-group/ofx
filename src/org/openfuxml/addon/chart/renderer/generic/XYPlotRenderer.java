package org.openfuxml.addon.chart.renderer.generic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.plot.XYPlot;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.ChartColorFactory;

public class XYPlotRenderer extends AbstractChartRenderer
{
	static Log logger = LogFactory.getLog(XYPlotRenderer.class);
	
	@Override
	protected void setSpecialColors()
	{
		XYPlot plot = (XYPlot) chart.getPlot();
	    plot.setRangeGridlinePaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.gridRange));
	    plot.setDomainGridlinePaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.gridDomain));
	}
	
	@Override
	protected void setSpecialGrid()
	{
		XYPlot plot = (XYPlot) chart.getPlot();
		Chart.Grid grid = ofxChart.getGrid();
	    if(grid.isSetDomain()){plot.setDomainGridlinesVisible(grid.isDomain());}
	    if(grid.isSetRange()){plot.setRangeGridlinesVisible(grid.isRange());}
	}
}