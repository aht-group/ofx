package org.openfuxml.addon.chart.renderer;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jfree.chart.JFreeChart;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver;

public class OFxChartRenderControl
{
	static Logger logger = Logger.getLogger(OFxChartRenderControl.class);
	
	public OFxChartRenderControl()
	{
		logger.debug(OFxChartRenderControl.class.getSimpleName()+" ready");
	}
	
	public JFreeChart render(Chart ofxChart)
	{
		OfxChartRenderer ofxRenderer=null;
		OfxChartTypeResolver.Type chartType = OfxChartTypeResolver.getType(ofxChart.getCharttype());
		switch(chartType)
		{
			case TimeSeries: ofxRenderer = new TimeSeriesChartRenderer();break;
			case TimeBar:    ofxRenderer = new TimeBarRenderer();break;
			case Bar: 		 ofxRenderer = new BarChartRenderer();break;
			default:	logger.warn("No Renderer available for "+chartType);
		}
		
		JFreeChart jfreeChart=ofxRenderer.render(ofxChart);
		return jfreeChart;
	}
	
	public JFreeChart render(Document doc)
	{
		Chart ofxChart = (Chart)JDomUtil.toJaxb(doc, Chart.class);
		return render(ofxChart); 
	}
}
