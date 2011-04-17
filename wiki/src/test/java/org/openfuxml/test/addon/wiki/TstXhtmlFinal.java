package org.openfuxml.test.addon.wiki;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;

public class TstXhtmlFinal
{
	static Log logger = LogFactory.getLog(TstXhtmlFinal.class);
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String txt = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><wiki></wiki>";
		int from = txt.indexOf("<wiki>")+6;
		int to = txt.lastIndexOf("</wiki>");
		logger.debug("From="+from+" To="+to);
			
		String input = WikiContentIO.loadTxt("resources/data/wiki/xhtmlFinal/2.xhtml");
		logger.debug("Input: "+input);
		
		XhtmlFinalProcessor pXhtml = new XhtmlFinalProcessor();
		String output = pXhtml.process(input);	
		logger.debug("Output: "+output);
    }
}