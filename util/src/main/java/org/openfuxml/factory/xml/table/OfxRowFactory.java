package org.openfuxml.factory.xml.table;

import org.openfuxml.content.table.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxRowFactory
{
	final static Logger logger = LoggerFactory.getLogger(OfxRowFactory.class);
	
	public static Row build(){return new Row();}
}