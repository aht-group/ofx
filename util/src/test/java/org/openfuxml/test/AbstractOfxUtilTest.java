package org.openfuxml.test;

import org.exlp.util.jx.JaxbUtil;
import org.junit.BeforeClass;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxUtilTest.class);

	@BeforeClass
    public static void initLogger()
	{
		OfxBootstrap.init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}