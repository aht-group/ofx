package org.openfuxml.addon.wiki.util;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Ofxchart;

public class OfxchartSelector
{
	static Logger logger = Logger.getLogger(OfxchartSelector.class);
	
	public static synchronized Ofxchart getChart(Ofx ofx, String id, String type)
	{
		for(Ofxchart ofxchart : ofx.getOfxchart())
		{
			if(ofxchart.getId().equals(id) && ofxchart.getType().equals(type)){return ofxchart;}
		}
		logger.warn("Chart not found for id="+id+" type="+type);
		return null;
	}
}
