package org.openfuxml.addon.wiki.util;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;

import com.aht.util.translation.Translation;

public class OfxchartUtil
{
	static Logger logger = Logger.getLogger(OfxchartUtil.class);
	
	public static synchronized Ofxchart getChart(Ofx ofx, String id, String type)
	{
		for(Ofxchart ofxchart : ofx.getOfxchart())
		{
			if(ofxchart.getId().equals(id) && ofxchart.getType().equals(type)){return ofxchart;}
		}
		logger.warn("Chart not found for id="+id+" type="+type);
		return null;
	}
	
	public static synchronized Ofxchart translate(Ofxchart ofxchart, Translation translation, String lang)
	{
		if(ofxchart.isSetXAxis() && ofxchart.getXAxis().isSetKey())
		{
			String label = translation.translate(ofxchart.getXAxis().getKey(),lang);
			ofxchart.getXAxis().setLabel(label);
		}
		return ofxchart;
	}
}
