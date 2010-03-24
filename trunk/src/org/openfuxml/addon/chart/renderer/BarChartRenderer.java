package org.openfuxml.addon.chart.renderer;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class BarChartRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(BarChartRenderer.class);
	
	public BarChartRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
        JFreeChart chart = ChartFactory.createBarChart(
        		ChartLabelResolver.getTitle(ofxChart), ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
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
}
