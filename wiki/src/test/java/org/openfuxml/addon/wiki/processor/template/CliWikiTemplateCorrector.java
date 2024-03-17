package org.openfuxml.addon.wiki.processor.template;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.io.log.LoggerInit;
import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CliWikiTemplateCorrector extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateCorrector.class);
	
	public static void main (String[] args) throws Exception
	{
		OfxBootstrap.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.addString(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnOfx = config.getString("wiki.processor.test.template.correct");
		Document ofxDoc = JaxbUtil.loadJAXB(fnOfx, Document.class);
		
		WikiTemplateCorrector templateCorrector = new WikiTemplateCorrector();
		templateCorrector.correctTemplateInjections(ofxDoc);
		
	}
}