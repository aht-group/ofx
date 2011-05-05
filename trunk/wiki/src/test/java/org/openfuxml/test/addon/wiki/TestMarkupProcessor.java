package org.openfuxml.test.addon.wiki;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.renderer.cmp.Cmp;

public class TestMarkupProcessor
{
	static Log logger = LogFactory.getLog(TestMarkupProcessor.class);
	
	private WikiMarkupProcessor wmp;

	@BeforeClass
    public static void initLogger()
	{
		LoggerInit loggerInit = new LoggerInit("log4junit.xml");	
		loggerInit.addAltPath("src/test/resources/config");
		loggerInit.init();
    }
	
	@Before
	public void initTest() throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB("src/test/resources/config/cmp.xml", Cmp.class);
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wmp = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
	}
	
    @Test
    public void testFor()
    {
        assertEquals("0","0");
    }
    
    @Test
    public void testFor2()
    {
        assertEquals("0","0");
    }
	
	public void scanSrcDir(String srcName) throws OfxInternalProcessingException
	{
		File srcDir = new File(srcName);
		logger.debug(srcDir.getAbsolutePath());
		for(File f : srcDir.listFiles())
		{
			if(f.getName().endsWith(".txt"))
			{
				wikiPlainToMarkup(f);
			}
		}
	}
	
	private void wikiPlainToMarkup(File srcFile) throws OfxInternalProcessingException
	{
		logger.debug(srcFile.getAbsolutePath());
		String txt = WikiContentIO.loadTxt(srcFile);
		String result = wmp.process(txt, "article ... req?");
		
		logger.debug(txt);
		logger.debug(result);
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();
		
//		ConfigLoader.add("resources/properties/user.properties");
//		Configuration config = ConfigLoader.init();			
		
		TestMarkupProcessor test = new TestMarkupProcessor();
		
		test.initTest();
		test.scanSrcDir("src/test/resources/data/wiki/plain");
    }
}