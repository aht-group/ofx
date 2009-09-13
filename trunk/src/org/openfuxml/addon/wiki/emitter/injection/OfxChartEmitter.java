package org.openfuxml.addon.wiki.emitter.injection;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.emitter.NestingEmitter;
import org.openfuxml.addon.wiki.util.JdomXmlStreamer;
import org.xml.sax.Attributes;

public class OfxChartEmitter
{
	private static Logger logger = Logger.getLogger(OfxChartEmitter.class);

	public OfxChartEmitter(Wikiinjection tmpInjection)
	{
		
	}
	
	public void transform(JdomXmlStreamer jdomStreamer)
	{
		Element e = new Element("hihixx");
		e.setAttribute("id","1");
		
		try {
			jdomStreamer.write(e);
		} catch (XMLStreamException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}