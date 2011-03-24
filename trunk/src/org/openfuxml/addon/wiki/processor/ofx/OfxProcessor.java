package org.openfuxml.addon.wiki.processor.ofx;

import java.io.File;
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

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.FormattingXMLStreamWriter;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiInOutProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiInOutProcessor;
import org.openfuxml.addon.wiki.util.IgnoreDtdEntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class OfxProcessor extends AbstractWikiInOutProcessor implements WikiInOutProcessor
{
	static Log logger = LogFactory.getLog(OfxProcessor.class);
	
	public OfxProcessor()
	{
		WikiTemplates.init();
	}
	
	public void process(Contents wikiQueries)
	{
		for(Content content : wikiQueries.getContent())
		{
			try
			{
				String srcName = WikiContentIO.getFileFromSource(content.getSource(), "xhtml");
				String dstName = WikiContentIO.getFileFromSource(content.getSource(), "xml");
				String txtMarkup = WikiContentIO.loadTxt(srcDir, srcName);
				String result = process(txtMarkup, "title");
				
				File fDst = new File(dstDir, dstName);
				Document doc = JDomUtil.txtToDoc(result);
				JDomUtil.save(doc, fDst, Format.getRawFormat());
			}
			catch (IOException e) {logger.error(e);}
			catch (ParserConfigurationException e) {logger.error(e);}
			catch (XMLStreamException e) {logger.error(e);}
			catch (SAXException e) {logger.error(e);}
			
		}
	}

	public String process(String xhtmlContent, String titleText) throws IOException, ParserConfigurationException, XMLStreamException, SAXException
	{
		
		Object[] objects = new Object[2];
		objects[0] = titleText;
		
		String header = MessageFormat.format(WikiTemplates.htmlHeader, objects);
		
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		sb.append(xhtmlContent);
		sb.append(WikiTemplates.htmlFooter);
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

		logger.warn("Using dummy String injectionDir");
		OfxHtmlContentHandler contentHandler = new OfxHtmlContentHandler(writer,".");

		xmlReader.setContentHandler(contentHandler);
		xmlReader.parse(inputSource);

		writer.close();

		String result = out.toString();
		result = addNS(result);
//		logger.debug(result);
		return result;
	}
	
	private String addNS(String xml)
	{
		int indexXml = xml.indexOf(">");
		int indexRoot = xml.substring(indexXml+1, xml.length()).indexOf(">");
		
//		logger.debug(xml.substring(indexXml+indexRoot+1));
		StringBuffer sb = new StringBuffer();
		sb.append(xml.substring(0,indexXml+indexRoot+1));
		sb.append(" xmlns:ofx=\"http://www.openfuxml.org\" xmlns:wiki=\"http://www.openfuxml.org/wiki\"");
		sb.append(xml.substring(indexXml+indexRoot+1,xml.length()));
//		logger.debug(sb);
		
		return sb.toString();
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