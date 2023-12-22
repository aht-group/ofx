package org.openfuxml.addon.wiki.processor.template.transformator;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.addon.wiki.processor.template.exlp.event.WikiKeyValueEvent;
import org.openfuxml.addon.wiki.processor.template.exlp.parser.WikiKeyValueParser;
import org.openfuxml.model.xml.addon.wiki.Template;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.core.handler.EhResultContainer;
import net.sf.exlp.core.listener.LogListenerString;
import net.sf.exlp.interfaces.LogEvent;
import net.sf.exlp.interfaces.LogListener;
import net.sf.exlp.interfaces.LogParser;

public class WikiTemplateKeyValueTransformator
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateKeyValueTransformator.class);
	
	private NsPrefixMapperInterface nsPrefixMapper;
	private WikiInlineProcessor wikiInlineProcessor;
	
	public WikiTemplateKeyValueTransformator(WikiInlineProcessor wikiInlineProcessor) 
	{
		this.wikiInlineProcessor=wikiInlineProcessor;
		nsPrefixMapper = new OfxNsPrefixMapper();
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
}