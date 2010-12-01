package org.openfuxml.addon.chart.renderer.generic;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Axis.DateTicker;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.renderer.generic.AbstractChartRenderer.AxisType;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;

public class XYPlotRenderer extends AbstractChartRenderer
{
	static Logger logger = Logger.getLogger(XYPlotRenderer.class);
	
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
			logger.debug("checking="+ofxChart.getCharttype().getTimeseries().getTimePeriod());
			
			try
			{
				ofxTimePeriod = OfxChartTimePeriod.valueOf(ofxChart.getCharttype().getTimeseries().getTimePeriod());
			}
			catch(IllegalArgumentException e)
			{
				logger.error("timePeriod "+ofxChart.getCharttype().getTimeseries().getTimePeriod()+ " is not valid, using Hour as default");
				ofxTimePeriod = OfxChartTimePeriod.Hour;
			}
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
	
	@Override
	protected void setAxisRange(Axis axis,AxisType type)
	{
		logger.debug("Setting Axis Range");
		int level = axis.getDateTicker().size();
		logger.debug("Level: "+level);
		
		PeriodAxis pAxis = new PeriodAxis(null);
		pAxis.setAutoRangeTimePeriodClass(Month.class);
		pAxis.setMajorTickTimePeriodClass(Month.class);
	    
		PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[level];
		
		int i=0;
		for(DateTicker dt : axis.getDateTicker())
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dt.getFormat());
			OfxChartTimePeriod ofxTp = OfxChartTimePeriod.valueOf(dt.getTimePeriod());
			switch(ofxTp)
			{
				case Hour:  info[i] = new PeriodAxisLabelInfo(Hour.class,sdf);break;
				case Day:   info[i] = new PeriodAxisLabelInfo(Day.class,sdf);break;
				case Month: info[i] = new PeriodAxisLabelInfo(Month.class,sdf);break;
				case Year:  info[i] = new PeriodAxisLabelInfo(Year.class,sdf);break;
			}
			i++;
		}
	        
        pAxis.setLabelInfo(info);
	        
        XYPlot plot = (XYPlot) chart.getPlot();
        switch(type)
        {
        	case range:  plot.setRangeAxis(pAxis);break;
        	case domain: plot.setDomainAxis(pAxis);break;
        }
	}
	
	protected void setRangeAxis()
	{
		logger.warn("setRangeAxis is depreciated!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
/*	        PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[2];
	        info[0] = new PeriodAxisLabelInfo(Month.class,
	                new SimpleDateFormat("MMM"));//, new RectangleInsets(2, 2, 2, 2),
	        info[1] = new PeriodAxisLabelInfo(Year.class,
	                new SimpleDateFormat("yyyy"));
	        domainAxis.setLabelInfo(info);
*/	        plot.setDomainAxis(domainAxis);
//	        plot.setRangeAxis(domainAxis);
	}
}