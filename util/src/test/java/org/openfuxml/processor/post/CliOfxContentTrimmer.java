package org.openfuxml.processor.post;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

public class CliOfxContentTrimmer
{
	final static Logger logger = LoggerFactory.getLogger(OfxContentTrimmer.class);
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
		loggerInit.path("resources/config");
		loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.addString(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnOfx = config.getString("wiki.processor.test.contenttrimmer");
		Document doc = JDomUtil.load(fnOfx);
		
		OfxContentTrimmer test = new OfxContentTrimmer();
		test.trim(doc);
		JDomUtil.debug(doc);
	}
}