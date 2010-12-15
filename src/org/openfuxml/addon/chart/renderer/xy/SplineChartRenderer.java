package org.openfuxml.addon.chart.renderer.xy;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
	
	private Map<Integer,OfxCustomPaintColors> mapOfxColors;
	
	public SplineChartRenderer()
	{
		mapOfxColors = new Hashtable<Integer,OfxCustomPaintColors>();
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		this.ofxChart=ofxChart;
		
		ValueAxis xAxis = (ValueAxis)AxisFactory.createNumberAxis(ofxChart, OfxChartTypeResolver.AxisOrientation.domain);
        
        OfxSplineRenderer splineRenderer = new OfxSplineRenderer();
        splineRenderer.setOfxPaintColors(getOfxPaintColor(0));
        
        XYPlot plot = new XYPlot();
        plot.setDomainAxis(xAxis);
        
        plot.setRenderer(0,splineRenderer);
        plot.setRenderer(1,new OfxSplineRenderer());
        
        List<XYSeriesCollection> lData = createDataset3(ofxChart.getContainer());
        
        
        for(int i=0;i<lData.size();i++)
        {
        	plot.setRangeAxis(i, (ValueAxis)AxisFactory.createNumberAxis(ofxChart, OfxChartTypeResolver.AxisOrientation.range0));
    		plot.setDataset(i, lData.get(i));
    		plot.mapDatasetToRangeAxis(i, i);
        }

		chart = new JFreeChart(ChartLabelResolver.getTitle(ofxChart),
                JFreeChart.DEFAULT_TITLE_FONT, plot,
                ofxChart.isLegend());
		
		setColors();
        setGrid();
     
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
	
	protected List<XYSeriesCollection> createDataset3(List<Container> lContainer)
	{
		List<XYSeriesCollection> lData = new ArrayList<XYSeriesCollection>();
		
		XYSeriesCollection data = new XYSeriesCollection();
		XYSeriesCollection data2 = new XYSeriesCollection();
		
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
				if(c.getLabel().endsWith("prec"))
				{
					data2.addSeries(series);
				}
				else{data.addSeries(series);}
				getOfxPaintColor(0).addColorMapping(seriesIndex, colorIndex);
				seriesIndex++;
			}
			
			for(Container c2 : c.getContainer())
			{
				if(c2.isSetData())
				{
					logger.info(" Sub index="+seriesIndex+" "+c2.getLabel().endsWith("prec"));
					series = new XYSeries(c.getLabel()+"-"+c2.getLabel());
					for(Data d : c2.getData()){series.add(d.getX(), d.getY());}
					
					if(c2.getLabel().endsWith("prec"))
					{
						data2.addSeries(series);
					}
					else
					{
						data.addSeries(series);
					}
					
					getOfxPaintColor(0).addColorMapping(seriesIndex, colorIndex);
					seriesIndex++;
				}
			}
			
			colorIndex++;
		}
		
		lData.add(data);
		lData.add(data2);
		return lData;
	}
	
	private OfxCustomPaintColors getOfxPaintColor(int key)
	{
		if(!mapOfxColors.containsKey(key)){mapOfxColors.put(key, new OfxCustomPaintColors());}
		return mapOfxColors.get(key);
	}
}