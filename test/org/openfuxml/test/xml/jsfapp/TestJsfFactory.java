package org.openfuxml.test.xml.jsfapp;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.openfuxml.addon.jsfapp.factory.JsfJspxFactory;

public class TestJsfFactory
{
	private static Logger logger = Logger.getLogger(TestJsfFactory.class);
	
	public TestJsfFactory()
	{
		
	}
	
	public void test()
	{
		Document doc = JsfJspxFactory.createDOMjspx();
		JDomUtil.debug(doc);
	}
	
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing Metatag");
			
		TestJsfFactory test = new TestJsfFactory();
		test.test();
	}
}