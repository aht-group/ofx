package org.openfuxml.test.xml.wiki;

import info.bliki.wiki.model.WikiModel;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openfuxml.addon.wiki.OpenFuxmlGenerator;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.WikiTextFetcher;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processing.WikiProcessor;
import org.openfuxml.addon.wiki.processing.XhtmlProcessor;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.test.xml.wiki.docbook.DocbookGenerator;
import org.xml.sax.SAXException;

import de.kisner.util.LoggerInit;

public class TestProcessedJdom
{
	static Logger logger = Logger.getLogger(TestProcessedJdom.class);

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
			File f = new File("dist/"+article+"-"+TestWiki.Status.xhtmlProcessed+".xhtml");
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