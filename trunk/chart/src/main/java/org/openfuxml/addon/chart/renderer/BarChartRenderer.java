package org.openfuxml.addon.chart.renderer;

import java.awt.Dimension;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openfuxml.addon.chart.data.jaxb.Chart;
import org.openfuxml.addon.chart.data.jaxb.Container;
import org.openfuxml.addon.chart.data.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.AbstractChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class BarChartRenderer extends AbstractChartRenderer implements OfxChartRenderer
{
	static Log logger = LogFactory.getLog(BarChartRenderer.class);
	
	public BarChartRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
        JFreeChart chart = ChartFactory.createBarChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		ChartLabelResolver.getAxisLabelX(ofxChart),
        		ChartLabelResolver.getAxisLabelY(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		PlotOrientation.VERTICAL,
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        return chart;
	}
	
	private CategoryDataset createDataset(List<Container> lContainer)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Container container : lContainer)
		{
			for(Data data : container.getData())
			{
				dataset.addValue(data.getY(), container.getLabel(), data.getCategory());
			}	
		}
        return dataset;
    }
	
	public Dimension getSuggestedSize()
	{
		logger.fatal("This should be @Overridden");
		throw new UnsupportedOperationException();
	}
}
