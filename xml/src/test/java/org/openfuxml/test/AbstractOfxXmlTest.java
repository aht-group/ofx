package org.openfuxml.test;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.exlp.controller.handler.io.log.LoggerBootstrap;
import org.exlp.test.AbstractXmlTest;
import org.junit.BeforeClass;

public abstract class AbstractOfxXmlTest <T extends Object> extends AbstractXmlTest<T>
{
	public AbstractOfxXmlTest(Class<T> cXml, Path pSuffix)
	{
		super(cXml,Paths.get("ofx","system","io","jaxb"),pSuffix);
	}
	
	public static void initLogger()
	{
		LoggerBootstrap.instance().path("ofx/system/io/log").init();
    }
	
	@BeforeClass
	public static void initPrefixMapper()
	{
//		JaxbUtil.setNsPrefixMapper(new GeoJsfNsPrefixMapper());
	}
}