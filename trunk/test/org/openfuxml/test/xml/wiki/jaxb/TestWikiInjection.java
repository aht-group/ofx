package org.openfuxml.test.xml.wiki.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;

import de.kisner.util.LoggerInit;

public class TestWikiInjection
{
	private static Logger logger = Logger.getLogger(TestWikiInjection.class);
	
	public TestWikiInjection()
	{
		
	}
	
	public void xmlConstruct()
	{
		Wikiinjection wikiinjection = new Wikiinjection();
		wikiinjection.setWikitag("a");
		wikiinjection.setOfxtag("b");
		wikiinjection.setFormat("xml");
		wikiinjection.setValue("vvvalluuuuuu");
		
		Wikicontainer container = new Wikicontainer();
		container.getWikiinjection().add(wikiinjection);
		
		try
		{
			JAXBContext context = JAXBContext.newInstance(Wikicontainer.class);
			Marshaller m = context.createMarshaller(); 
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 
			m.marshal(container, System.out);
		}
		catch (JAXBException e) {logger.error(e);}
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		TestWikiInjection test = new TestWikiInjection();
			test.xmlConstruct();
	}
}