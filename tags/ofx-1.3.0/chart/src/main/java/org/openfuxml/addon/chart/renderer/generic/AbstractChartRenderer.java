package org.openfuxml.addon.chart.renderer.generic;

import java.awt.Dimension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.data.jaxb.Axis;
import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;

public class AbstractChartRenderer
{
	static Log logger = LogFactory.getLog(AbstractChartRenderer.class);
	
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
			AxisOrientation type = AxisOrientation.valueOf(axis.getCode());
			setAxis(axis,type);
		}
	}
	protected void setAxis(Axis axis,AxisOrientation type)
	{
		logger.fatal("This should be @Overridden");
	}
	
	public Dimension getSuggestedSize()
	{
		logger.fatal("This should be @Overridden");
		throw new UnsupportedOperationException();
	}
}