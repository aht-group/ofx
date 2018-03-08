package org.openfuxml.transform.xhtml.emitter;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.openfuxml.factory.txt.TxtTagFactory;
import org.openfuxml.interfaces.xml.OfxEmphasis;
import org.openfuxml.transform.xhtml.EmitterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

public class EmphasisEmitter extends NestingEmitter
{
	final static Logger logger = LoggerFactory.getLogger(EmphasisEmitter.class);
	
	private Map<String,String> attributes;
	private OfxEmphasis.Emphasis emphasis;
	
	public EmphasisEmitter(EmitterFactory ef, OfxEmphasis.Emphasis emphasis)
	{
		super(ef);
		this.emphasis=emphasis;
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
		writer.writeStartElement(TxtTagFactory.tag(OfxEmphasis.class));

		writeStyle(writer);
		writeEmphasis(writer);
		
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
	
	private void writeStyle(XMLStreamWriter writer) throws XMLStreamException
	{

	}
	
	private void writeEmphasis(XMLStreamWriter writer) throws XMLStreamException
	{
		switch(emphasis)
		{
			case bold: writer.writeAttribute("bold", "true");break;
			case italic: writer.writeAttribute("italic", "true");break;
		}
	}
}