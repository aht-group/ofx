package org.openfuxml.wiki.emitter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

public class HeaderEmitter implements Emitter
{
	private static Logger logger = Logger.getLogger(HeaderEmitter.class);
	
	private Pattern HEADER_ELEM_NAME_PATTERN = Pattern.compile("h(\\d)");

	private Emitter next;
	private int nestLevel = 0;
	private final int headerLevel;
	private String sectionName;
	private boolean dispatching = false;
	private boolean sectionOpen = false;
	private EmitterFactory ef;
	
	public HeaderEmitter(EmitterFactory ef, int headerLevel)
	{
		this.ef=ef;
		this.headerLevel = headerLevel;
		if (headerLevel == 1) {sectionName = "chapter";}
		else {sectionName = "section";}
		logger.debug("inited sectionName:"+sectionName);
	}

	public void content(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException {
		if (!dispatching && nestLevel == 1) {
			writer.writeCharacters(ch, start, length);
		} else {
			dispatchContent(writer, ch, start, length);
		}
	}

	public boolean end(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException {
		--nestLevel;
		if (!dispatching && nestLevel == 0) {
			closeTitle();
			dispatching = true;
		} else {
			dispatchEnd(writer, htmlElementName);
		}
		return false;
	}

	public boolean start(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		++nestLevel;
		if (!dispatching && nestLevel == 1) {openSection(atts);}
		else
		{
			Matcher matcher = HEADER_ELEM_NAME_PATTERN.matcher(htmlElementName);
			if (matcher.matches()) {
				int localLevel = Integer.parseInt(matcher.group(1));
				if (localLevel <= headerLevel) {
					closeSection();
					return false;
				}
			}
			dispatchStart(writer, htmlElementName, atts);
		}
		return true;
	}

	private void dispatchStart(XMLStreamWriter writer, String htmlElementName, Attributes atts) throws XMLStreamException {
		if (next == null) {
			next = ef.getEmitter(htmlElementName);
		}

		if (!next.start(writer, htmlElementName, atts)) {
			next.close();
			next = ef.getEmitter(htmlElementName);
			if (!next.start(writer, htmlElementName, atts)) {
				throw new IllegalStateException();
			}
		}
	}

	private void dispatchEnd(XMLStreamWriter writer, String htmlElementName) throws XMLStreamException {
		if (next != null) {
			if (next.end(writer, htmlElementName)) {
				next.close();
				next = null;
			}
		} else {
			throw new IllegalStateException();
		}
	}

	private void dispatchContent(XMLStreamWriter writer, char[] ch, int start, int length) throws XMLStreamException {
		if (next != null) {
			next.content(writer, ch, start, length);
		} else {
			writer.writeCharacters(ch, start, length);
		}
	}

	private void openSection(Attributes atts) throws XMLStreamException
	{
		sectionOpen = true;
		ef.getWriter().writeStartElement(sectionName);
		ef.getWriter().writeStartElement("title");

		String elementId = atts.getValue("id");
		if (elementId != null)
		{
			ef.getWriter().writeAttribute("id", elementId);
		}
	}

	private void closeSection() throws XMLStreamException {
		if (next != null) {
			next.close();
			next = null;
		}
		ef.getWriter().writeEndElement(); // section
		next = null;
		dispatching = false;
		sectionOpen = false;
	}

	private void closeTitle() throws XMLStreamException {
		ef.getWriter().writeEndElement(); // title
	}

	public void close() throws XMLStreamException {
		if (next != null) {
			next.close();
			next = null;
		}
		if (sectionOpen) {
			closeSection();
		}
	}
}