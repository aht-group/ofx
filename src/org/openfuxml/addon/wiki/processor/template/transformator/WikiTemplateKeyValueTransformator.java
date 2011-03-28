package org.openfuxml.addon.wiki.processor.template.transformator;

import net.sf.exlp.event.LogEventHandler;
import net.sf.exlp.event.handler.EhDebug;
import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.listener.LogListener;
import net.sf.exlp.listener.impl.LogListenerString;
import net.sf.exlp.parser.LogParser;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.jdom.Namespace;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.template.exlp.parser.WikiKeyValueParser;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;

public class WikiTemplateKeyValueTransformator extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateKeyValueTransformator.class);
	
	private final Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
	
	public WikiTemplateKeyValueTransformator() 
	{
		
	}
	
	public Element transform(String wikiMarkup)
	{
		LogEventHandler leh = new EhDebug();
		LogParser lp = new WikiKeyValueParser(leh);
		LogListener ll = new LogListenerString(wikiMarkup,lp);
		ll.processSingle();
		
		Element e = new Element("paragraph");
		e.setNamespace(nsOfx);
		e.setText("This is the content!!");
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
			
		String fnTemplate = config.getString("wiki.processor.test.template.kv");
		Template template = (Template)JaxbUtil.loadJAXB(fnTemplate, Template.class);
		
		WikiTemplateKeyValueTransformator kvTransformator = new WikiTemplateKeyValueTransformator();
		Element e = kvTransformator.transform(template.getMarkup().getValue());
		JDomUtil.debug(e);
	}
}