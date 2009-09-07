package org.openfuxml.addon.wiki.emitter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.Attributes;

public class AnchorEmitter extends NestingEmitter
{
	private boolean openTag = false;

	public AnchorEmitter(EmitterFactory ef)
	{
		super(ef);
	}

	@Override
	public boolean localStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException
	{
		String href = null;
		String name = null;
		if (htmlElementName.equals("a"))
		{
			href = atts.getValue("href");
			name = atts.getValue("name");
		}
		if (name != null && name.length() > 0)
		{
			openTag = true;
			writer.writeStartElement("phrase");
			writer.writeAttribute("id", name);
		}
		else if (href != null)
		{
			if (href.startsWith("#"))
			{
				if (href.length() > 1) {
					openTag = true;
					writer.writeStartElement("link");
					writer.writeAttribute("linkend", href.substring(1));
				}
			}
			else
			{
				if(href.startsWith("file"))
				{
					
				}
				else
				{
					openTag = true;
					writer.writeStartElement("ulink");
					writer.writeAttribute("url", href);
				}
			}
		}
		return true;
	}

	@Override
	protected boolean localEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException
	{
		closeTag(writer);
		return true;
	}

	private void closeTag(XMLStreamWriter writer) throws XMLStreamException
	{
		if (openTag)
		{
			writer.writeEndElement();
			openTag = false;
		}
	}

	@Override
	public void close() throws XMLStreamException
	{
		super.close();
		closeTag(ef.getWriter());
	}
}