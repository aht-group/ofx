package org.openfuxml.test.xml.jsftaglib.jaxb;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.JsfTagTransformator;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;

import de.kisner.util.LoggerInit;

public class TestJsfTagTransformator
{
	private static Logger logger = Logger.getLogger(TestJsfTagTransformator.class);
	
	private Taglib taglib;
	private JsfTagTransformator tagTransformator;
	
	public TestJsfTagTransformator()
	{
		 tagTransformator = new JsfTagTransformator(new File("dist"),2); 
	}
	
	public void readTaglib(String xmlFile)
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Taglib.class);
			Unmarshaller u = jc.createUnmarshaller();
			taglib = (Taglib)u.unmarshal(mrl.searchIs(xmlFile));
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public void transform()
	{
		tagTransformator.transform(taglib);
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestJsfTagTransformator test = new TestJsfTagTransformator();
			test.readTaglib(args[0]);
			test.transform();
	}
}