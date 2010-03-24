package org.openfuxml.addon.chart.renderer;

import java.awt.Color;
import java.util.Date;
import java.util.List;

import net.sf.exlp.util.DateUtil;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.util.ChartColorFactory;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.addon.wiki.util.OfxColorFactory;

public class TimeSeriesChartRenderer implements OfxChartRenderer
{
	static Logger logger = Logger.getLogger(TimeSeriesChartRenderer.class);
	
	private boolean renderLegend;
	
	public TimeSeriesChartRenderer()
	{
		logger.debug("Active");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart), ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        
        XYPlot plot = (XYPlot) chart.getPlot();
        
        //Colors
	    chart.setBackgroundPaint(ChartColorFactory.createColor(ofxChart, ChartColorFactory.Area.backgroundChart));
//	    plot.setBackgroundPaint(ChartColorFactory.createColor(ofxChart, "plot-background", Color.WHITE));
//	    plot.setRangeGridlinePaint(ChartColorFactory.createColor(ofxChart, "range-grid", Color.LIGHT_GRAY));
//	    plot.setDomainGridlinePaint(ChartColorFactory.createColor(ofxChart, "domain-grid", Color.LIGHT_GRAY));
        
        return chart;
	}
	
	private TimeSeriesCollection createDataset(List<Container> lContainer)
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
	
	public JFreeChart createChart(Ofxchart ofxChart)
	{
        JFreeChart chart = null;
        
        XYPlot plot = (XYPlot) chart.getPlot();
        
	    //Colors
	    chart.setBackgroundPaint(OfxColorFactory.createColor(ofxChart, "chart-background", Color.WHITE));
	    plot.setBackgroundPaint(OfxColorFactory.createColor(ofxChart, "plot-background", Color.WHITE));
	    plot.setRangeGridlinePaint(OfxColorFactory.createColor(ofxChart, "range-grid", Color.LIGHT_GRAY));
	    plot.setDomainGridlinePaint(OfxColorFactory.createColor(ofxChart, "domain-grid", Color.LIGHT_GRAY));
        
	    //Grids
	    Ofxchart.Grid grid = ofxChart.getGrid();
	    plot.setDomainGridlinesVisible(grid.isDomain());  
        plot.setRangeGridlinesVisible(grid.isRange()); 
	    
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer)
        {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
        }
        return chart;
	}
	
	public boolean isRenderLegend() {return renderLegend;}
	public void setRenderLegend(boolean renderLegend) {this.renderLegend = renderLegend;}
}
