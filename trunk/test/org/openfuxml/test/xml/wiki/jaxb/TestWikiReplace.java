package org.openfuxml.test.xml.wiki.jaxb;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;

import de.kisner.util.LoggerInit;

public class TestWikiReplace
{
	@SuppressWarnings("unused")
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
		
		Ofx container = new Ofx();
		container.getWikireplace().add(wikireplace);
		
		JaxbUtil.debug(container);
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