package org.openfuxml.addon.chart.util;

import java.awt.Font;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.openfuxml.addon.chart.jaxb.AxisType;
import org.openfuxml.addon.chart.jaxb.AxisType.Date.Ticker;
import org.openfuxml.addon.chart.jaxb.Chart;
import org.openfuxml.addon.chart.jaxb.Label;
import org.openfuxml.addon.chart.util.OfxChartTypeResolver.AxisOrientation;
import org.openfuxml.addon.chart.util.TimePeriodFactory.OfxChartTimePeriod;

public class AxisFactory
{
	static Log logger = LogFactory.getLog(AxisFactory.class);
	
	public static synchronized Axis createNumberAxis(Chart ofxChart, AxisOrientation type)
	{
		org.openfuxml.addon.chart.jaxb.Axis ofxAxis = AxisFactory.getAxis(ofxChart, type);
		
		Axis axis = null;
		switch(OfxChartTypeResolver.getAxisType(ofxAxis.getAxisType()))
		{
			case Number: axis = createNumberAxis(ofxAxis);break;
		}
		
		
		return axis;
	}
	
	public static synchronized NumberAxis createNumberAxis(org.openfuxml.addon.chart.jaxb.Axis ofxAxis)
	{
		NumberAxis numAxis = new NumberAxis();
		boolean autoRangeIncludesZero = true;
		if(ofxAxis.isSetAutoRangIncludeNull()){autoRangeIncludesZero = ofxAxis.isAutoRangIncludeNull();}
		numAxis.setAutoRangeIncludesZero(autoRangeIncludesZero);
		labelAxisAxis(numAxis, ofxAxis);
		return numAxis;
	}
	
	public static synchronized PeriodAxis createPeriodAxis(org.openfuxml.addon.chart.jaxb.Axis ofxAxis)
	{
		AxisType.Date ofxDateAxis = ofxAxis.getAxisType().getDate();
		int level = ofxDateAxis.getTicker().size();
		logger.debug("Level: "+level);
		
		PeriodAxis axis = new PeriodAxis(null);
		axis.setAutoRangeTimePeriodClass(Month.class);
		axis.setMajorTickTimePeriodClass(Month.class);
		
		if(ofxDateAxis.isSetAutoRangeTimePeriod())
		{
			axis.setAutoRangeTimePeriodClass(TimePeriodFactory.getPeriodClass(ofxDateAxis.getAutoRangeTimePeriod()));
		}
		if(ofxDateAxis.isSetMajorTickTimePeriod())
		{
			axis.setMajorTickTimePeriodClass(TimePeriodFactory.getPeriodClass(ofxDateAxis.getMajorTickTimePeriod()));
		}
		
		PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[level];
		int i=0;
		for(Ticker dt : ofxAxis.getAxisType().getDate().getTicker())
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dt.getFormat());
			OfxChartTimePeriod ofxTp = OfxChartTimePeriod.valueOf(dt.getTimePeriod());
			switch(ofxTp)
			{
				case Hour:  info[i] = new PeriodAxisLabelInfo(Hour.class,sdf);break;
				case Day:   info[i] = new PeriodAxisLabelInfo(Day.class,sdf);break;
				case Month: info[i] = new PeriodAxisLabelInfo(Month.class,sdf);break;
				case Year:  info[i] = new PeriodAxisLabelInfo(Year.class,sdf);break;
			}
			i++;
		}
		axis.setLabelInfo(info);
		
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
		if(ofxLabel.isSetFont()){fontFamily = ofxLabel.getFont();}
		if(ofxLabel.isSetSize()){fontSize=ofxLabel.getSize();}
		
		Font font = new Font(fontFamily, Font.PLAIN, fontSize);
		return font;
	}
	
	private static synchronized org.openfuxml.addon.chart.jaxb.Axis getAxis(Chart ofxChart, AxisOrientation type)
	{
		org.openfuxml.addon.chart.jaxb.Axis axisResult = null;
		for(org.openfuxml.addon.chart.jaxb.Axis axis : ofxChart.getAxis())
		{
			if(axis.isSetCode() && axis.getCode().equals(type.toString())){axisResult = axis;}
		}
		return axisResult;
	}
}
