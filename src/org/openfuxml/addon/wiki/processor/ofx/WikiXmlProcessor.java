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
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.FormattingXMLStreamWriter;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.util.IgnoreDtdEntityResolver;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.util.xml.OfxNsPrefixMapper;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class WikiXmlProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiXmlProcessor.class);
	
	private OfxNsPrefixMapper nsPrefixMapper;
	
	public WikiXmlProcessor()
	{
		WikiTemplates.init();
		nsPrefixMapper = new OfxNsPrefixMapper();
	}
	
	@Override
	protected void processCategory(Content content)
	{
		Sections sections = new Sections();
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			Section section = new Section();
			section.setExternal(true);
			section.setSource(dstDir.getName()+"/"+page.getFile()+"."+WikiProcessor.WikiFileExtension.xml);
			sections.getContent().add(section);
			processPage(page);
		}
		String fName = WikiContentIO.getFileFromSource(content.getSource())+"."+WikiProcessor.WikiFileExtension.xml;
		File f = new File(dstDir,fName);
		logger.debug("Writing categories external XML: "+f.getAbsolutePath());
		JaxbUtil.save(f, sections, nsPrefixMapper,true);
	}
	
	@Override
	protected void processPage(Content content)
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	public void processPage(Page page)
	{
		try
		{
			String srcName =  page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
			String dstName = page.getFile()+"."+WikiProcessor.WikiFileExtension.xml;
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