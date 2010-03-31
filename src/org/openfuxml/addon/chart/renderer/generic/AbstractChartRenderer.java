package org.openfuxml.addon.chart.renderer.generic;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.ChartColorFactory;

public class AbstractChartRenderer
{
	static Logger logger = Logger.getLogger(AbstractChartRenderer.class);
	
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
}