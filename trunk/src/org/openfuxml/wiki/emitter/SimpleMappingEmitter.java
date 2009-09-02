package org.openfuxml.wiki.emitter;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.openfuxml.wiki.HtmlToOpenFuxmlContentHandler;
import org.xml.sax.Attributes;


public class SimpleMappingEmitter extends NestingEmitter
{
	private static Logger logger = Logger.getLogger(HtmlToOpenFuxmlContentHandler.class);
	
	private final String[] tags;

	private Map<String, String> attributes;

	public SimpleMappingEmitter(EmitterFactory ef, String... docbookTagNames)
	{
		super(ef);
		tags = docbookTagNames;
	}

	public void setAttribute(String name, String value) {
		if (attributes == null) {
			attributes = new TreeMap<String, String>();
		}
		attributes.put(name, value);
	}

	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
//		logger.debug("localEnd "+tags.length);
		for (int x = 0; x < tags.length; ++x)
		{
			writer.writeEndElement();
		}
		return true;
	}

	@Override
	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		for (String tag : tags)
		{
			writer.writeStartElement(tag);
		}
		boolean hasId = false;
		if (attributes != null)
		{
			for (Map.Entry<String, String> attr : attributes.entrySet())
			{
				writer.writeAttribute(attr.getKey(), attr.getValue());
			}
			if (attributes.containsKey("id")) {hasId = true;}
		}
		if(!hasId)
		{
			String elementId = atts.getValue("id");
			if (elementId != null)
			{
				writer.writeAttribute("id", elementId);
			}
		}
		return true;
	}
}