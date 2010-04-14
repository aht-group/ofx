package org.openfuxml.addon.chart.renderer;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class TimeSeriesChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(TimeSeriesChartRenderer.class);
	
	public TimeSeriesChartRenderer()
	{
		logger.debug("Active");
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
	
	private TimeSeriesCollection createDataset(List<Container> lContainer)
	{
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(Container container : lContainer)
		{
			TimeSeries ts = new TimeSeries(container.getLabel());
			for(Data data : container.getData())
			{
					Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
					ts.addOrUpdate(new Hour(d), data.getY());
			}
			dataset.addSeries(ts);	
		}
		return dataset;
	}
}
