package org.openfuxml.addon.wiki.processor.template.transformator;

import org.apache.commons.configuration.Configuration;
import org.exlp.controller.handler.system.property.ConfigLoader;
import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.model.xml.addon.wiki.Template;
import org.openfuxml.test.OfxBootstrap;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class CliWikiTemplateKeyValueTransformator
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateKeyValueTransformator.class);
	
	public static void main (String[] args) throws Exception
	{
		OfxBootstrap.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
		ConfigLoader.addString(propFile);
		Configuration config = ConfigLoader.init();
			
		String fNameCmp = config.getString("ofx.xml.cmp");
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
		WikiInlineProcessor wikiInline = new WikiInlineProcessor(cmp);
		
		String fnTemplate = config.getString("wiki.processor.test.template.kv");
		Template template = (Template)JaxbUtil.loadJAXB(fnTemplate, Template.class);
		
		Template templateDef = new Template();
		templateDef.setClazz("xx");
		
		WikiTemplateKeyValueTransformator kvTransformator = new WikiTemplateKeyValueTransformator(wikiInline);
		Element e = kvTransformator.transform(templateDef,template);
		JDomUtil.debug(e);
	}
}