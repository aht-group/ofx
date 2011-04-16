package org.openfuxml.addon.wiki.processor.template.transformator;

import net.sf.exlp.event.LogEvent;
import net.sf.exlp.event.handler.EhResultContainer;
import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerString;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.WikiInlineProcessor;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.template.exlp.event.WikiKeyValueEvent;
import org.openfuxml.addon.wiki.processor.template.exlp.parser.WikiKeyValueParser;
import org.openfuxml.renderer.data.jaxb.Cmp;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class WikiTemplateKeyValueTransformator
{
	static Log logger = LogFactory.getLog(WikiTemplateKeyValueTransformator.class);
	
	private Namespace nsOfx;
	private NsPrefixMapperInterface nsPrefixMapper;
	private WikiInlineProcessor wikiInlineProcessor;
	
	public WikiTemplateKeyValueTransformator(WikiInlineProcessor wikiInlineProcessor) 
	{
		this.wikiInlineProcessor=wikiInlineProcessor;
		nsPrefixMapper = new OfxNsPrefixMapper();
		nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	}
	
	public Element transform(Template templateDef, Template template)
	{
		EhResultContainer leh = new EhResultContainer();
		LogParser lp = new WikiKeyValueParser(leh);
		LogListener ll = new LogListenerString(template.getMarkup().getValue(),lp);
		ll.processSingle();
		
		for(LogEvent logEvent : leh.getAlResults())
		{
			WikiKeyValueEvent kvEvent= (WikiKeyValueEvent)logEvent;
			template.getTemplateKv().add(kvEvent.getKv());
		}
		
		return transformWithClass(templateDef, template);
	}
	
	private Element transformWithClass(Template templateDef, Template template)
	{
		WikiTemplateGenericTable genericTable = new WikiTemplateGenericTable(nsPrefixMapper);
		genericTable.setWikiInlineProcessor(wikiInlineProcessor);
		Element e = genericTable.transform(template);
		return e;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
		ConfigLoader.add(propFile);
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