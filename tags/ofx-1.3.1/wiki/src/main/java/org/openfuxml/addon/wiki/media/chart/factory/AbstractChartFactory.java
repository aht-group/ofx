package org.openfuxml.addon.wiki.media.chart.factory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class AbstractChartFactory
{
	final static Logger logger = LoggerFactory.getLogger(AbstractChartFactory.class);
	
	protected double getChartValue(String xp, String type, Document doc)
	{
		double value=0;
		try
		{
			XPath xPath = XPath.newInstance(xp+"[@type='"+type+"']");
			Element element = (Element)xPath.selectSingleNode(doc);
			value = new Double(element.getTextTrim());
		}
		catch (JDOMException e) {logger.error("",e);}
		return value;
	}
}
