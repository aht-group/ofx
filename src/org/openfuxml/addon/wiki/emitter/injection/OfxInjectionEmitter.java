package org.openfuxml.addon.wiki.emitter.injection;

import java.util.Hashtable;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.emitter.EmitterFactory;
import org.openfuxml.addon.wiki.emitter.NestingEmitter;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.xml.sax.Attributes;

public class OfxInjectionEmitter extends NestingEmitter
{
	private static Logger logger = Logger.getLogger(OfxInjectionEmitter.class);

	public OfxInjectionEmitter(EmitterFactory ef, String... ofxTagNames)
	{
		super(ef);
	}

	@Override
	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		Wikiinjection injection = new Wikiinjection();
		injection.setFormat(atts.getValue("format"));
		injection.setOfxtag(atts.getValue("ofxtag"));
		injection.setWikitag(atts.getValue("wikitag"));
		
		JdomXmlStreamer jdomStreamer = new JdomXmlStreamer(writer);
		if(injection.getOfxtag().equals("ofxchart"))
		{
			OfxChartEmitter chartEmitter = new OfxChartEmitter(injection);
			chartEmitter.transform(jdomStreamer);
		}
		else{logger.warn("Unkown ofxtag: "+injection.getOfxtag());}
		return true;
	}
}