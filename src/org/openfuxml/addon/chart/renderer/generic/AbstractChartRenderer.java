package org.openfuxml.addon.chart.renderer.generic;

import java.awt.Dimension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.jaxb.Axis;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.ChartColorFactory;

public class AbstractChartRenderer
{
	static Log logger = LogFactory.getLog(AbstractChartRenderer.class);
	
	public static enum AxisType{range,domain};
	
	protected JFreeChart chart;
	protected Chart ofxChart;
	
	public AbstractChartRenderer()
	{
		
	}
	
	protected void setColors()
	{
		chart.setBackgroundPaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.backgroundChart));
		chart.getPlot().setBackgroundPaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.backgroundPlot));
		setSpecialColors();
	}
	protected void setSpecialColors(){logger.fatal("This should be @Overridden");}
	
	protected void setGrid()
	{
		 if(ofxChart.isSetGrid())
		 {
			 setSpecialGrid();
		 }
	}
	protected void setSpecialGrid(){logger.fatal("This should be @Overridden");}
	
	protected void setAxis()
	{
		for(Axis axis : ofxChart.getAxis())
		{
			AxisType type = AxisType.valueOf(axis.getCode());
			setAxisRange(axis,type);
		}
	}
	protected void setAxisRange(Axis axis,AxisType type){logger.fatal("This should be @Overridden");}
	
	public Dimension getSuggestedSize()
	{
		logger.fatal("This should be @Overridden");
		throw new UnsupportedOperationException();
	}
}