package org.openfuxml.addon.wiki;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.util.IgnoreDtdEntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class OpenFuxmlGenerator
{
	static Log logger = LogFactory.getLog(OpenFuxmlGenerator.class);

	private Configuration config;
	
	public OpenFuxmlGenerator(Configuration config)
	{
		this.config=config;
	}

	public String create(String xhtmlContent, String footer, String titleText) throws IOException, ParserConfigurationException, XMLStreamException, SAXException
	{
		Object[] objects = new Object[2];
		objects[0] = titleText;
		String header = MessageFormat.format(WikiTemplates.htmlHeader, objects);

		logger.debug("Header ist: "+header);
		
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		sb.append(xhtmlContent);
		sb.append(footer);
		logger.debug("Parsing: "+sb.length()+" characters");

		InputSource inputSource = new InputSource(new StringReader(sb.toString()));

		SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(false);
		SAXParser saxParser = factory.newSAXParser();

		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setEntityResolver(IgnoreDtdEntityResolver.getInstance());

		StringWriter out = new StringWriter();
		XMLStreamWriter writer = createXMLStreamWriter(out);

		HtmlToOpenFuxmlContentHandler contentHandler = new HtmlToOpenFuxmlContentHandler(writer,config);

		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(inputSource);

		writer.close();

		return out.toString();
	}

	protected XMLStreamWriter createXMLStreamWriter(Writer out)
	{
		XMLStreamWriter writer;
		try
		{
			writer = XMLOutputFactory.newInstance().createXMLStreamWriter(out);
		}
		catch (XMLStreamException e1) {throw new IllegalStateException(e1);}
		catch (FactoryConfigurationError e1) {throw new IllegalStateException(e1);}
		return new FormattingXMLStreamWriter(writer);
	}
}