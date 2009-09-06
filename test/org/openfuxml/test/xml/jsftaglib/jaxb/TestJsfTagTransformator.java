package org.openfuxml.test.xml.jsftaglib.jaxb;

import java.io.File;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.JsfTagTransformator;

import de.kisner.util.LoggerInit;

public class TestJsfTagTransformator
{
	private static Logger logger = Logger.getLogger(TestJsfTagTransformator.class);
	
	private JsfTagTransformator tagTransformator;
	
	public TestJsfTagTransformator(String xmlFile)
	{
		logger.debug(JsfTagTransformator.class.getSimpleName()+" will be tested");
		tagTransformator = new JsfTagTransformator(new File("dist"),2); 
		tagTransformator.readTaglib(xmlFile);
		tagTransformator.transform();
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		new TestJsfTagTransformator(args[0]);
	}
}