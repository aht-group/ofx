package org.openfuxml.addon.chart;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Charttype;
import org.openfuxml.addon.chart.renderer.BarChartRenderer;
import org.openfuxml.addon.chart.renderer.TimeBarRenderer;
import org.openfuxml.addon.chart.renderer.generic.OfxChartRenderer;
import org.openfuxml.addon.chart.renderer.timeseries.TimeSeriesChartRenderer;
import org.openfuxml.addon.chart.renderer.timeseries.TimeSeriesCumulativeChartRenderer;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;

public class OFxChartRenderControl
{
	static Logger logger = Logger.getLogger(OFxChartRenderControl.class);
	
	public OFxChartRenderControl()
	{
		logger.warn("here");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		logger.warn("here");
		OfxChartRenderer ofxRenderer=null;
		OfxChartTypeResolver.Type chartType = OfxChartTypeResolver.getType(ofxChart.getCharttype());
		switch(chartType)
		{
			case TimeSeries: ofxRenderer = getTimeSeriesRenderer(ofxChart);break;
			case TimeBar:    ofxRenderer = new TimeBarRenderer();break;
			case Bar: 		 ofxRenderer = new BarChartRenderer();break;
			default:	logger.warn("No Renderer available for "+chartType);
		}
		
		JFreeChart jfreeChart=ofxRenderer.render(ofxChart);
		return jfreeChart;
	}
	
	private OfxChartRenderer getTimeSeriesRenderer(Chart ofxChart)
	{
		OfxChartRenderer result;
		Charttype.Timeseries timeseries = ofxChart.getCharttype().getTimeseries();
		if(timeseries.isSetCumulate() && timeseries.isCumulate()){result = new TimeSeriesCumulativeChartRenderer();}
		else {result = new TimeSeriesChartRenderer();}
		return result;
	}
	
	public JFreeChart render(Document doc)
	{
		Chart ofxChart = (Chart)JDomUtil.toJaxb(doc, Chart.class);
		return render(ofxChart); 
	}
}
