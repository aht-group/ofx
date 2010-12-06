package org.openfuxml.addon.chart.util;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OfxCustomPaintColors
{
	static Log logger = LogFactory.getLog(OfxCustomPaintColors.class);
	
	private boolean customColorsPalette;
	
	private Map<Integer,Integer> mapColorIndex;
	
	public OfxCustomPaintColors()
	{
		customColorsPalette = false;
		mapColorIndex = new Hashtable<Integer,Integer>();
	}
	
	public int getColorIndex(int series)
	{
		if(mapColorIndex.size()==0){return series;}
		else
		{
			int index = mapColorIndex.get(series);
//			logger.debug("Index: "+series+" -> "+index);
			return index;
		}
	}
	
	public void addColorMapping(int series, int indexColor)
	{
		mapColorIndex.put(series, indexColor);
	}
	
	public boolean isCustomColorsPalette() {return customColorsPalette;}
}
