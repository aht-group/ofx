package org.openfuxml.addon.chart.renderer.xy;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Container;
import org.openfuxml.addon.chart.jaxb.Data;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.generic.XYPlotRenderer;
import org.openfuxml.addon.chart.util.AxisFactory;
import org.openfuxml.addon.chart.util.ChartLabelResolver;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;
import org.openfuxml.addon.chart.util.OfxCustomPaintColors;

public class SplineChartRenderer extends XYPlotRenderer implements OfxChartRenderer
{
	static Log logger = LogFactory.getLog(SplineChartRenderer.class);
	
	private OfxCustomPaintColors ofxColors;
	
	public SplineChartRenderer()
	{
		
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		
		ValueAxis xAxis = (ValueAxis)AxisFactory.createNumberAxis(ofxChart, OfxChartTypeResolver.AxisOrientation.domain);
		ValueAxis yAxis = (ValueAxis)AxisFactory.createNumberAxis(ofxChart, OfxChartTypeResolver.AxisOrientation.range);
		
        ofxColors = new OfxCustomPaintColors();
        XYSeriesCollection xySeriesCollection = createDataset(ofxChart.getContainer());
        
        OfxSplineRenderer splineRenderer = new OfxSplineRenderer();
 //       splineRenderer.setOfxPaintColors(ofxColors);
        
        XYPlot plot = new XYPlot(xySeriesCollection, xAxis, yAxis, splineRenderer);
		
		chart = new JFreeChart(ChartLabelResolver.getTitle(ofxChart),
                JFreeChart.DEFAULT_TITLE_FONT, plot,
                ofxChart.isLegend());
		
		setColors();
        setGrid();
//        setAxis();
/*        chart = ChartFactory.createTimeSeriesChart(
        		ChartLabelResolver.getTitle(ofxChart),
        		ChartLabelResolver.getXaxis(ofxChart),ChartLabelResolver.getYaxis(ofxChart),
        		createDataset(ofxChart.getContainer()),
        		ofxChart.isLegend(),
	            true,               // generate tooltips?
	            false               // generate URLs?
	        );
        
       
 */       
        return chart;
	}
	
	protected RegularTimePeriod getRtp(Date d)
	{
		RegularTimePeriod rtp;
		switch(ofxTimePeriod)
		{
			case Hour: rtp = new Hour(d);break;
			case Day: rtp = new Day(d);break;
			case Month: rtp = new Month(d);break;
			default: rtp = new Hour(d);break;
		}
		return rtp;
	}
	
	protected XYSeriesCollection createDataset(List<Container> lContainer)
	{
		XYSeriesCollection data = new XYSeriesCollection();
		
		int colorIndex=0;
		int seriesIndex=0;
		for(Container c : lContainer)
		{
			XYSeries series;
			if(c.isSetData())
			{
				logger.info("Container: index="+seriesIndex);
				series = new XYSeries(c.getLabel());
				for(Data d : c.getData()){series.add(d.getX(), d.getY());}
				data.addSeries(series);
				ofxColors.addColorMapping(seriesIndex, colorIndex);
				seriesIndex++;
			}
			
			for(Container c2 : c.getContainer())
			{
				if(c2.isSetData())
				{
					logger.info(" Sub index="+seriesIndex);
					series = new XYSeries(c.getLabel()+"-"+c2.getLabel());
					for(Data d : c2.getData()){series.add(d.getX(), d.getY());}
					data.addSeries(series);
					ofxColors.addColorMapping(seriesIndex, colorIndex);
					seriesIndex++;
				}
			}
			
			colorIndex++;
		}
		
		return data;
	}
}
