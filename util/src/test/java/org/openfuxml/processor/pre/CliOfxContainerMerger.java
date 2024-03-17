package org.openfuxml.processor.pre;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Document;
import org.openfuxml.test.OfxBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class CliOfxContainerMerger
{
	final static Logger logger = LoggerFactory.getLogger(CliOfxContainerMerger.class);
	
	public static void main (String[] args) throws Exception
	{
		OfxBootstrap.init();
		
		logger.debug("Testing ExternalMerger");
		
		String fName;
//		fName = "resources/data/xml/preprocessor/merge/container/sections.xml";
		fName = "resources/data/xml/preprocessor/merge/container/transparent.xml";
		if(args.length == 1 ){fName = args[0];}
		
		Document ofxDocOriginal = JaxbUtil.loadJAXB(fName, Document.class);
		JaxbUtil.debug(ofxDocOriginal);
		
		OfxContainerMerger containerMerger = new OfxContainerMerger();
//		Document ofxDocContainer = containerMerger.merge(ofxDocOriginal);
//		JaxbUtil.debug(ofxDocContainer);
	}
}