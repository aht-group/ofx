package org.openfuxml.addon.chart.renderer;

import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Charttype.Timebar;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.ChartLabelResolver;

public class TimeBarRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Log logger = LogFactory.getLog(TimeBarRenderer.class);
	
	public TimeBarRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		Timebar timebar = ofxChart.getCharttype().getTimebar();
        chart = ChartFactory.createXYBarChart(
        	ChartLabelResolver.getTitle(ofxChart), ChartLabelResolver.getXaxis(ofxChart),
            true,
            ChartLabelResolver.getYaxis(ofxChart),                        // range axis label
            createDataset(ofxChart.getContainer()),                    // data
            PlotOrientation.VERTICAL,
            ofxChart.isLegend(),                       // include legend
            true,
            false
        );

        
        setColors();
        setGrid();
        
        XYPlot plot = (XYPlot) chart.getPlot();
        ClusteredXYBarRenderer renderer = new ClusteredXYBarRenderer(0.0, false);
        
        if(timebar.isSetShadow()){renderer.setShadowVisible(timebar.isShadow());}
        if(timebar.isSetGradient() && timebar.isGradient())
        {
        	renderer.setBarPainter(new StandardXYBarPainter());
        }
        renderer.setDrawBarOutline(false);
        
        plot.setRenderer(renderer);
        return chart;
	}

	private IntervalXYDataset createDataset(List<Container> lContainer)
	{
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for(Container container : lContainer)
		{
			TimeSeries ts = new TimeSeries(container.getLabel());
			for(Data data : container.getData())
			{
					Date d = DateUtil.getDateFromInt(data.getRecord().getYear(), data.getRecord().getMonth(), data.getRecord().getDay());
					ts.addOrUpdate(new Hour(d), data.getY());
			}
			 dataset.addSeries(ts);
		}
		return dataset;
	}
}
