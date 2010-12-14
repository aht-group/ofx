package org.openfuxml.addon.chart.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Label;

public class AxisFactory
{
	static Log logger = LogFactory.getLog(AxisFactory.class);

	public static enum AxisType{range,domain};
	
	public static synchronized Axis createNumberAxis(Chart ofxChart, AxisType type)
	{
		org.openfuxml.addon.chart.jaxb.Axis ofxAxis = AxisFactory.getAxis(ofxChart, type);
		
		Axis axis = null;
		
		boolean numberAx = true;
		if(numberAx)
		{
			NumberAxis numAxis = new NumberAxis();
			
			boolean autoRangeIncludesZero = true;
			if(ofxAxis.isSetAutoRangIncludeNull()){autoRangeIncludesZero = ofxAxis.isAutoRangIncludeNull();}
			numAxis.setAutoRangeIncludesZero(autoRangeIncludesZero);
			//		axis.setLabelFont(new Font("SansSerif", Font.ITALIC, 55));
			axis = numAxis;
		}
		labelAxisAxis(axis, ofxAxis);
		
		return axis;
	}
	
	public static synchronized void labelAxisAxis(Axis axis, org.openfuxml.addon.chart.jaxb.Axis ofxAxis)
	{
		if(ofxAxis.isSetLabel())
		{
			Label ofxLabel = ofxAxis.getLabel();
			if(ofxLabel.isSetText())
			{
				axis.setLabel(ofxLabel.getText());
			}
		}
	}
	
	private static synchronized org.openfuxml.addon.chart.jaxb.Axis getAxis(Chart ofxChart, AxisType type)
	{
		org.openfuxml.addon.chart.jaxb.Axis axisResult = null;
		for(org.openfuxml.addon.chart.jaxb.Axis axis : ofxChart.getAxis())
		{
			if(axis.isSetCode() && axis.getCode().equals(type.toString())){axisResult = axis;}
		}
		return axisResult;
	}
}
