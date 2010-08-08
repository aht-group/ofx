package org.openfuxml.addon.chart.renderer.timeseries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public abstract class AbstractTimeSeriesChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(AbstractTimeSeriesChartRenderer.class);
	
	public static enum OfxChartTimePeriod {Hour,Day,Month};
	protected OfxChartTimePeriod ofxTimePeriod;
	
	public AbstractTimeSeriesChartRenderer()
	{
		
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		
		if(ofxChart.getCharttype().getTimeseries().isSetTimePeriod())
		{
			ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getCharttype().getTimeseries().getTimePeriod());
		}
		else
		{
			logger.warn("chart/charttype/timeseries/@timePeriod is not set!!");
			logger.warn("Using default: Hour");
			ofxTimePeriod=OfxChartTimePeriod.Hour;
		}
		
        chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        setColors();
        setGrid();
        setPeriodAxis();
       
        
        return chart;
	}
	
	private void setPeriodAxis()
	{
		switch(ofxTimePeriod)
		{
			case Month: setMonthPeriodAxis();break;
		}
	}
	
	private void setMonthPeriodAxis()
	{
		 XYPlot plot = (XYPlot) chart.getPlot();
	     PeriodAxis domainAxis = new PeriodAxis("");
	  //      domainAxis.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
	        domainAxis.setAutoRangeTimePeriodClass(Month.class);
	        domainAxis.setMajorTickTimePeriodClass(Month.class);
	        PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[2];
	        info[0] = new PeriodAxisLabelInfo(Month.class,
	                new SimpleDateFormat("MMM"));//, new RectangleInsets(2, 2, 2, 2),
	   //             new Font("SansSerif", Font.BOLD, 10), Color.blue, false,
	    //            new BasicStroke(0.0f), Color.lightGray);
	        info[1] = new PeriodAxisLabelInfo(Year.class,
	                new SimpleDateFormat("yyyy"));
	        domainAxis.setLabelInfo(info);
	        plot.setDomainAxis(domainAxis);
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
	
	protected TimeSeriesCollection createDataset(List<Container> lContainer)
	{
		logger.warn("This method must be @overridden");
		return null;
	}
}
