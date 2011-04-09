package org.openfuxml.addon.wiki.processor.ofx.emitter;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.processor.ofx.EmitterFactory;
import org.xml.sax.Attributes;

public class OfxListEmitter extends NestingEmitter
{
	static Log logger = LogFactory.getLog(OfxListEmitter.class);

	private Map<String, String> attributes;

	private final static String tag = "list:list";
	
	public OfxListEmitter(EmitterFactory ef)
	{
		super(ef);
	}

	public void setAttribute(String name, String value)
	{
		if (attributes == null)
		{
			attributes = new TreeMap<String, String>();
		}
		attributes.put(name, value);
	}

	@Override
	protected boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		writer.writeStartElement(tag);

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
	
	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
		writer.writeEndElement();
		return true;
	}
}