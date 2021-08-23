package org.openfuxml.addon.wiki.processor.template;

import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.xpath.XPath;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliWikiTemplateCorrector extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateCorrector.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnOfx = config.getString("wiki.processor.test.template.correct");
		Document ofxDoc = JaxbUtil.loadJAXB(fnOfx, Document.class);
		
		WikiTemplateCorrector templateCorrector = new WikiTemplateCorrector();
		templateCorrector.correctTemplateInjections(ofxDoc);
		
	}
}