package org.openfuxml.addon.chart.renderer.generic;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.ChartColorFactory;

public class XYPlotRenderer extends AbstractChartRenderer
{
	static Logger logger = Logger.getLogger(XYPlotRenderer.class);
	
	public static enum OfxChartTimePeriod {Hour,Day,Month};
	protected OfxChartTimePeriod ofxTimePeriod;
	
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
	
	protected void setTimePeriod()
	{
		if(ofxChart.getCharttype().isSetTimeseries() && ofxChart.getCharttype().getTimeseries().isSetTimePeriod())
		{
			ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getCharttype().getTimeseries().getTimePeriod());
		}
		else if(ofxChart.getCharttype().isSetGantt() && ofxChart.getCharttype().getGantt().isSetTimePeriod())
		{
			ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getCharttype().getGantt().getTimePeriod());
		}
		else
		{
			logger.warn("chart/charttype/timeseries/@timePeriod is not set!!");
			logger.warn("Using default: Hour");
			ofxTimePeriod=OfxChartTimePeriod.Hour;
		}
	}
	
	protected void setRangeAxis()
	{
		switch(ofxTimePeriod)
		{
			case Month: setMonthPeriodAxis();break;
		}
	}
	
	protected void setMonthPeriodAxis()
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
//	        plot.setDomainAxis(domainAxis);
	        plot.setRangeAxis(domainAxis);
	}
}