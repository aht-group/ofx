package org.openfuxml.test.xml.jsftaglib.jaxb;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.log4j.Logger;
import org.openfuxml.addon.jsf.data.jaxb.Attribute;
import org.openfuxml.addon.jsf.data.jaxb.ObjectFactory;
import org.openfuxml.addon.jsf.data.jaxb.Tag;
import org.openfuxml.addon.jsf.data.jaxb.Taglib;
import org.openfuxml.wiki.data.jaxb.Wikicontainer;

import de.kisner.util.LoggerInit;

public class TestTaglib
{
	private static Logger logger = Logger.getLogger(TestTaglib.class);
	
	public TestTaglib()
	{
		
	}
	
	public void xmlConstruct()
	{	
		ObjectFactory tagFactory = new ObjectFactory();
		
		Taglib taglib = new Taglib();
		taglib.setTlibversion("1.0");
		taglib.setJspversion("2.1");
		taglib.setShortname("jwan");
		taglib.setUri("http://www.openfuxml.org");
		taglib.setInfo("OpenFuXML");
		
		Tag tag = new Tag();
		tag.setName("myTag");
		tag.setInfo("info");
		
		Attribute att = new Attribute();
		att.setName("myAtt");
		att.setRequired(false);
		
		taglib.getTag().add(tag);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(Taglib.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(taglib, System.out);
		}
		catch (JAXBException e) {logger.error(e);}
	}
	
	public void xmlRead(String xmlFile)
	{
		MultiResourceLoader mrl = new MultiResourceLoader();
		Taglib taglib=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Taglib.class);
			Unmarshaller u = jc.createUnmarshaller();
			taglib = (Taglib)u.unmarshal(mrl.searchIs(xmlFile));
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
		
		logger.debug("Shortname: "+taglib.getShortname());
		logger.debug("Info "+taglib.getInfo());
		for(Tag tag : taglib.getTag())
		{
			logger.debug("   Tag: "+tag.getName());
			for(Attribute att : tag.getAttribute())
			{
				logger.debug("      Att: "+att.getName());
			}
		}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestTaglib test = new TestTaglib();
			test.xmlConstruct();
			test.xmlRead(args[0]);
	}
}