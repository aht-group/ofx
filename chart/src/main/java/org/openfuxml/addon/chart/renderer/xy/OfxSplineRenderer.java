package org.openfuxml.addon.chart.renderer.xy;

import java.awt.Paint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.openfuxml.addon.chart.util.OfxCustomPaintColors;

public class OfxSplineRenderer extends XYSplineRenderer
{
	private static final long serialVersionUID = 1;
	
	static Log logger = LogFactory.getLog(SplineChartRenderer.class);
	
	private OfxCustomPaintColors ofxPaintColors;
	
	public OfxSplineRenderer()
	{
	}
	
	public Paint getSeriesPaint(int series)
	{
		if(ofxPaintColors!=null)
		{
			return ofxPaintColors.getSeriesPaint(series);
		}
		else
		{
			return super.getSeriesPaint(series);
		}
	}
	
	public OfxCustomPaintColors getOfxPaintColors() {return ofxPaintColors;}
	public void setOfxPaintColors(OfxCustomPaintColors ofxPaintColors) {this.ofxPaintColors = ofxPaintColors;}

}
