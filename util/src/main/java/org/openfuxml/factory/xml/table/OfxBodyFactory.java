package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxBodyFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxBodyFactory.class);
	
	public static Body build()
	{
		Body xml = new Body();
		return xml;
	}
}