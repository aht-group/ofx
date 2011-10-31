package org.openfuxml.addon.wiki.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;
import org.openfuxml.util.translation.DoubleMapTranslation;

public class OfxchartUtil
{
	static Log logger = LogFactory.getLog(OfxchartUtil.class);
	
	public static synchronized Ofxchart getChart(Ofx ofx, String id, String type)
	{
		for(Ofxchart ofxchart : ofx.getOfxchart())
		{
			if(ofxchart.getId().equals(id) && ofxchart.getType().equals(type)){return ofxchart;}
		}
		logger.warn("Chart not found for id="+id+" type="+type);
		return null;
	}
	
	public static synchronized Ofxchart translate(Ofxchart ofxchart, DoubleMapTranslation translation, String lang)
	{
		if(ofxchart.isSetXAxis() && ofxchart.getXAxis().isSetKey())
		{
			String label = translation.translate(ofxchart.getXAxis().getKey(),lang);
			ofxchart.getXAxis().setLabel(label);
		}
		if(ofxchart.isSetYAxis() && ofxchart.getYAxis().isSetKey())
		{
			String label = translation.translate(ofxchart.getYAxis().getKey(),lang);
			ofxchart.getYAxis().setLabel(label);
		}
		if(ofxchart.isSetTitle() && ofxchart.getTitle().isSetKey())
		{
			String label = translation.translate(ofxchart.getTitle().getKey(),lang);
			ofxchart.getTitle().setLabel(label);
		}
		return ofxchart;
	}
}
