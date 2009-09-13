package org.openfuxml.addon.wiki.chart.factory;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class AbstractChartFactory
{
	private static Logger logger = Logger.getLogger(AbstractChartFactory.class);
	
	protected double getChartValue(String xp, String type, Document doc)
	{
		double value=0;
		try
		{
			XPath xPath = XPath.newInstance(xp+"[@type='"+type+"']");
			Element element = (Element)xPath.selectSingleNode(doc);
			value = new Double(element.getTextTrim());
		}
		catch (JDOMException e) {logger.error(e);}
		return value;
	}
}
