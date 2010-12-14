package org.openfuxml.addon.chart.renderer.generic;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.AxisFactory;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;
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
	protected void setAxis(Axis ofxAxis,AxisOrientation axisOrientation)
	{
		JaxbUtil.debug(ofxAxis);
		ValueAxis axis=null;
		switch(OfxChartTypeResolver.getAxisType(ofxAxis.getAxisType()))
		{
			case Number: axis = AxisFactory.createNumberAxis(ofxAxis);break;
			case Date: axis = AxisFactory.createPeriodAxis(ofxAxis);break;
			default: axis = new NumberAxis();AxisFactory.labelAxisAxis(axis, ofxAxis);
		}
		
		if(axis!=null)
		{
			XYPlot plot = (XYPlot) chart.getPlot();
	        switch(axisOrientation)
	        {
	        	case range:  plot.setRangeAxis(axis);break;
	        	case domain: plot.setDomainAxis(axis);break;
	        }
		}

	}
}