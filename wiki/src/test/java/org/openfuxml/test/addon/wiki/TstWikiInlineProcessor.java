package org.openfuxml.test.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.io.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.WikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.data.jaxb.Cmp;

public class TstWikiInlineProcessor
{
	static Log logger = LogFactory.getLog(TstWikiInlineProcessor.class);
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		File f = new File("resources/data/wiki/wikiPlain/1.txt");
		String wikiTxt = WikiContentIO.loadTxt(f);
		
		String fNameCmp = config.getString("ofx.xml.cmp");
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
		
		WikiInlineProcessor wikiInline = new WikiInlineProcessor(cmp);
		Section section = wikiInline.toOfx(wikiTxt);
		JaxbUtil.debug(section);
    }
}