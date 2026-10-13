package org.openfuxml.test;

import org.exlp.util.jx.JaxbUtil;
import org.junit.jupiter.api.BeforeAll;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractOfxUtilTest
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxUtilTest.class);

	@BeforeAll
    public static void initLogger()
	{
		OfxBootstrap.init();
    }
	
	@BeforeAll
	public static void initPrefixMapper()
	{
		//TOOD reactivate the prefix mapper
//		JaxbUtil.setNsPrefixMapper(new OfxNsPrefixMapper());
	}
}