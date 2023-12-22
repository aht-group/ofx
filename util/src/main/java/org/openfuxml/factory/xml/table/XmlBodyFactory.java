package org.openfuxml.factory.xml.table;

import org.openfuxml.model.xml.core.table.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBodyFactory
{
	final static Logger logger = LoggerFactory.getLogger(XmlBodyFactory.class);
	
	public static Body build()
	{
		Body xml = new Body();
		return xml;
	}
}