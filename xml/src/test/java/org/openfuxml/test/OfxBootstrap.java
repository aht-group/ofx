package org.openfuxml.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxBootstrap
{
	final static Logger logger = LoggerFactory.getLogger(OfxBootstrap.class);
		
	public static void init()
	{
		LoggerBootstrap.instance("ofx.log4j2.xml").path("ofx/system/io/log").init();
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}