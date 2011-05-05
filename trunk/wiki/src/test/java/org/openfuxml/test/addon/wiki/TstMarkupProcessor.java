package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.renderer.cmp.Cmp;

public class TstMarkupProcessor
{
	static Log logger = LogFactory.getLog(TstMarkupProcessor.class);
	
	private WikiMarkupProcessor wmp;
	
	public TstMarkupProcessor(String cmpFileName) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
	{
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(cmpFileName, Cmp.class);
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wmp = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
			
		
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
		
		TstMarkupProcessor test = new TstMarkupProcessor("src/test/resources/config/cmp.xml");
		test.scanSrcDir("src/test/resources/data/wiki/plain");
    }
}