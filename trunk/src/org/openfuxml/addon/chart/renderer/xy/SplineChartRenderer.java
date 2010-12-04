package org.openfuxml.addon.chart.renderer.xy;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.AxisResolver;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class SplineChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Log logger = LogFactory.getLog(SplineChartRenderer.class);
	
	public SplineChartRenderer()
	{
		
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		
		NumberAxis xAxis = new NumberAxis(ChartLabelResolver.getXaxis(ofxChart));
        xAxis.setAutoRangeIncludesZero(AxisResolver.includeNullInAutoRange(ofxChart,"x"));
        NumberAxis yAxis = new NumberAxis(ChartLabelResolver.getYaxis(ofxChart));
        yAxis.setAutoRangeIncludesZero(AxisResolver.includeNullInAutoRange(ofxChart,"y"));
		
        XYSplineRenderer renderer1 = new XYSplineRenderer();
        XYPlot plot = new XYPlot(createDataset(ofxChart.getContainer()),
        		 xAxis, yAxis, renderer1);
		
		chart = new JFreeChart(ChartLabelResolver.getTitle(ofxChart),
                JFreeChart.DEFAULT_TITLE_FONT, plot,
                ofxChart.isLegend());
		
		setColors();
        setGrid();
//        setAxis();
/*        chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        
       
 */       
        return chart;
	}
	
	protected RegularTimePeriod getRtp(Date d)
	{
		RegularTimePeriod rtp;
		switch(ofxTimePeriod)
		{
			case Hour: rtp = new Hour(d);break;
			case Day: rtp = new Day(d);break;
			case Month: rtp = new Month(d);break;
			default: rtp = new Hour(d);break;
		}
		return rtp;
	}
	
	protected XYSeriesCollection createDataset(List<Container> lContainer)
	{
		XYSeriesCollection data = new XYSeriesCollection();
		
		for(Container c : lContainer)
		{
			XYSeries series = new XYSeries(c.getLabel());
			for(Data d : c.getData())
			{
				series.add(d.getX(), d.getY());
			}
			data.addSeries(series);
		}
		
		
		return data;
	}
}
