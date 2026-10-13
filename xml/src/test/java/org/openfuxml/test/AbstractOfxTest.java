package org.openfuxml.test;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOfxTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxTest.class);
		
	@BeforeAll
    public static void initLogger()
	{
		LoggerBootstrap.instance("ofx.log4j2.xml").path("ofx/system/io/log").init();
    }
	
	@BeforeAll
	public static void initPrefixMapper()
	{
		logger.warn("Init "+OfxNsPrefixMapper.class.getSimpleName());
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}