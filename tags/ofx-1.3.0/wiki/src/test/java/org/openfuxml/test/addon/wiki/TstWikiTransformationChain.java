package org.openfuxml.test.addon.wiki;

import java.io.FileNotFoundException;

import net.sf.exlp.util.io.LoggerInit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;

@RunWith(Parameterized.class)
public class TstWikiTransformationChain
{
	static Log logger = LogFactory.getLog(TstWikiTransformationChain.class);
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("src/test/resources/config");
			loggerInit.init();	
		
		int id = 6;
		boolean saveReference=true;
			
		TestMarkupProcessor.chain();
		TestModelProcessor.chain(id,saveReference);
		TestXhtmlReplaceProcessor.chain(id,saveReference);
		TestXhtmlFinalProcessor.chain(id,saveReference);
		TestWikiXmlProcessor.chain(id,saveReference);
    }
}