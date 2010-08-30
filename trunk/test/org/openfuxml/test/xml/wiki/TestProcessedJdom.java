package org.openfuxml.test.xml.wiki;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openfuxml.addon.wiki.OfxWikiEngine;
import org.openfuxml.addon.wiki.WikiTemplates;

import de.kisner.util.LoggerInit;

public class TestProcessedJdom
{
	static Log logger = LogFactory.getLog(TestProcessedJdom.class);

	private String dirName;
	private Configuration config;
	
	public TestProcessedJdom(Configuration config,String dirName)
	{
		this.config=config;
		this.dirName=dirName;
	}
	
	private void testProcessedJdom()
	{
		String article = config.getString("wiki/article");
		Document doc = null;
		try
		{
			File f = new File("dist/"+article+"-"+OfxWikiEngine.Status.xhtmlProcessed+".xhtml");
			doc = new SAXBuilder().build(f);
		}
		catch (JDOMException e) {e.printStackTrace();}
		catch (IOException e) {logger.error(e);}
	}
		
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
			
		WikiTemplates.init();	
			
		TestProcessedJdom tw = new TestProcessedJdom(config,"dist");
		tw.testProcessedJdom();
    }
}