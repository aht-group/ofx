package org.openfuxml.addon.wiki.util;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdomXmlStreamer
{	
	final static Logger logger = LoggerFactory.getLogger(JdomXmlStreamer.class);
	
	private XMLStreamWriter writer;
	
	public JdomXmlStreamer(XMLStreamWriter writer)
	{
		this.writer=writer;
	}
	
	public void write(Element rootElement) throws XMLStreamException
	{
		writer.writeStartElement(rootElement.getName());
		for(Object o : rootElement.getAttributes())
		{
			Attribute a = (Attribute)o;
			writer.writeAttribute(a.getName(), a.getValue());
		}
		for(Object o : rootElement.getContent())
		{
			if(org.jdom.Text.class.isInstance(o))
			{
				Text txt = (Text)o;
				writer.writeCharacters(txt.getText());
			}
			else if(org.jdom.Element.class.isInstance(o))
			{
				Element child = (Element)o;
				write(child);
			}
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		writer.writeEndElement();
	}
}
