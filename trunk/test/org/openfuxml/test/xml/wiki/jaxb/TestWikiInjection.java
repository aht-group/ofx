package org.openfuxml.test.xml.wiki.jaxb;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.ObjectFactory;
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
		ObjectFactory of = new ObjectFactory();
		
		Wikiinjection wikiinjection = new Wikiinjection();
		wikiinjection.setWikitag("a");
		wikiinjection.setOfxtag("b");
		wikiinjection.setFormat("xml");
		wikiinjection.setWikicontent(of.createWikiinjectionWikicontent());
		wikiinjection.getWikicontent().setValue("vvvalluuuuuu");
		
		Wikicontainer container = new Wikicontainer();
		container.getWikiinjection().add(wikiinjection);
		
		JaxbUtil.debug(container);
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