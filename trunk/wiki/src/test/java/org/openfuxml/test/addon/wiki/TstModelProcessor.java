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

public class TstModelProcessor
{
	static Log logger = LogFactory.getLog(TstModelProcessor.class);
	
	public TstModelProcessor()
	{
		
	}
	
	public void test()
	{
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/properties/user.properties");
		Configuration config = ConfigLoader.init();			
			
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(config.getString("ofx.xml.cmp"), Cmp.class);
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		WikiMarkupProcessor wmp = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(), templates);
			
		File f = new File("resources/data/wiki/1.txt");
		String txt = WikiContentIO.loadTxt(f);
		String result = wmp.process(txt, "article ... req?");
		
		logger.debug(txt);
		logger.debug(result);
    }
}