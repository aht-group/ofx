package org.openfuxml.addon.chart.util;

import java.awt.Font;

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
		
		JaxbUtil.debug(ofxAxis.getAxisType());
		switch(OfxChartTypeResolver.getAxisType(ofxAxis.getAxisType()))
		{
			case Number: axis = createNumberAxis(ofxAxis);break;
		}
		labelAxisAxis(axis, ofxAxis);
		
		return axis;
	}
	
	public static synchronized NumberAxis createNumberAxis(org.openfuxml.addon.chart.jaxb.Axis ofxAxis)
	{
		NumberAxis numAxis = new NumberAxis();
		
		boolean autoRangeIncludesZero = true;
		if(ofxAxis.isSetAutoRangIncludeNull()){autoRangeIncludesZero = ofxAxis.isAutoRangIncludeNull();}
		numAxis.setAutoRangeIncludesZero(autoRangeIncludesZero);
		//		axis.setLabelFont();
		return numAxis;
	}
	
	public static synchronized void labelAxisAxis(Axis axis, org.openfuxml.addon.chart.jaxb.Axis ofxAxis)
	{
		if(ofxAxis.isSetLabel())
		{
			Label ofxLabel = ofxAxis.getLabel();
			if(ofxLabel.isSetText())
			{
				axis.setLabel(ofxLabel.getText());
				if(ofxLabel.isSetSize() || ofxLabel.isSetFont())
				{
					axis.setLabelFont(createFont(ofxLabel));
				}
			}
		}
	}
	
	private static synchronized Font createFont(Label ofxLabel)
	{
		String fontFamily = "SansSerif";
		int fontSize=10;
		if(ofxLabel.isSetFont()){}
//		if(ofxLabel.isSetSize()){fontSize=ofxLabel.getSize();}
		
		Font font = new Font(fontFamily, Font.PLAIN, fontSize);
		return font;
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
