package org.openfuxml.transform;

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

import org.exlp.util.jx.JaxbUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.openfuxml.addon.wiki.FormattingXMLStreamWriter;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processor.ofx.OfxHtmlContentHandler;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.util.IgnoreDtdEntityResolver;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.trancoder.XhtmlSpecialChars;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XhtmlTransformer extends AbstractWikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(XhtmlTransformer.class);
	
//	private OfxContentTrimmer ofxContentTrimmer;
//	import org.openfuxml.renderer.processor.post.OfxContentTrimmer;
	
	public XhtmlTransformer()
	{
		WikiTemplates.init();
//		ofxContentTrimmer = new OfxContentTrimmer();
	}
		
	public Section process(String htmlContent)
	{
//		logger.info(htmlContent);
		org.jsoup.nodes.Document sDoc = Jsoup.parse(htmlContent);
		sDoc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
	    String xhtmlContent = sDoc.body().html();
//	    logger.info(xhtmlContent);
	    
		try
		{
			String xml = xhtml2Ofx(xhtmlContent);
			if(logger.isTraceEnabled()){logger.info(xml);}
			
//			Document doc = JDomUtil.txtToDoc(xml);
//			JDomUtil.debug(doc);
			
			Section section = JaxbUtil.load(xml.getBytes("UTF-8"),Section.class);
			return section;
		}
		catch (IOException e) {logger.error("",e);}
		catch (ParserConfigurationException e) {logger.error("",e);}
		catch (XMLStreamException e) {logger.error("",e);}
		catch (SAXException e)
		{
			System.out.println(xhtmlContent);
			logger.error("",e);
		}
//		catch (JDOMException e) {logger.error("",e);}
		return null;
	}

	private String xhtml2Ofx(String xhtmlContent) throws IOException, ParserConfigurationException, XMLStreamException, SAXException
	{
		xhtmlContent = XhtmlSpecialChars.replace(xhtmlContent);
		Object[] objects = new Object[1];
		objects[0] = xhtmlContent;
		
		String html = MessageFormat.format(TransformerTemplates.html, objects);
		if(logger.isTraceEnabled()){logger.info(html);}
				
//		logger.info("Parsing: "+html.toString()+" characters");

		InputSource inputSource = new InputSource(new StringReader(html));

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setValidating(false);
		SAXParser saxParser = factory.newSAXParser();

		XMLReader xmlReader = saxParser.getXMLReader();
		xmlReader.setEntityResolver(IgnoreDtdEntityResolver.getInstance());

		StringWriter out = new StringWriter();
		XMLStreamWriter writer = createXMLStreamWriter(out);

		logger.trace("Using dummy String injectionDir");
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

		StringBuffer sb = new StringBuffer();
		sb.append(xml.substring(0,indexXml+indexRoot+1));
		sb.append(" xmlns:ofx=\"http://www.openfuxml.org\"");
		sb.append(" xmlns:").append(OfxNsPrefixMapper.prefix(OfxNsPrefixMapper.NS.text)).append("=\"").append(OfxNsPrefixMapper.namespace(OfxNsPrefixMapper.NS.text)).append("\"");
		sb.append(" xmlns:list=\"http://www.openfuxml.org/list\"");
		sb.append(" xmlns:table=\"http://www.openfuxml.org/table\"");
		sb.append(" xmlns:layout=\"http://www.openfuxml.org/layout\"");
		sb.append(" xmlns:wiki=\"http://www.openfuxml.org/wiki\"");
		sb.append(xml.substring(indexXml+indexRoot+1,xml.length()));
		
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