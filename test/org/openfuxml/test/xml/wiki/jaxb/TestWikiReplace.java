package org.openfuxml.test.xml.wiki.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;

import de.kisner.util.LoggerInit;

public class TestWikiReplace
{
	private static Logger logger = Logger.getLogger(TestWikiReplace.class);
	
	public TestWikiReplace()
	{
		
	}
	
	public void xmlConstruct()
	{
		Wikireplace wikireplace = new Wikireplace();
		wikireplace.setFrom("a");
		wikireplace.setTo("b");
		wikireplace.setDescription("Description");
		
		Wikicontainer container = new Wikicontainer();
		container.getWikireplace().add(wikireplace);
		
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
		
		TestWikiReplace test = new TestWikiReplace();
			test.xmlConstruct();
	}
}