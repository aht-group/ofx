package org.openfuxml.addon.chart.renderer.timeseries;

import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public abstract class AbstractTimeSeriesChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(AbstractTimeSeriesChartRenderer.class);
	
	public AbstractTimeSeriesChartRenderer()
	{

	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
        chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart), ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        setColors();
        setGrid();
        return chart;
	}
	
	protected TimeSeriesCollection createDataset(List<Container> lContainer)
	{
		logger.warn("This method must be @overridden");
		return null;
	}
}
