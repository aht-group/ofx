package org.openfuxml.addon.wiki.processor.template;

import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;
import org.openfuxml.util.xml.OfxNsPrefixMapper;

public class WikiTemplateCorrector extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateCorrector.class);
	
	private final String startDelimiter = "&lt;wiki:injection id=&quot;";
	private final String endDelimiter = "&quot;/&gt;";
	
	private OfxNsPrefixMapper nsPrefixMapper;
	private XPath xpath;
	
	public WikiTemplateCorrector() 
	{
		nsPrefixMapper = new OfxNsPrefixMapper();
		
		try
		{
			Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			
			xpath = XPath.newInstance("//wiki:template");
			xpath.addNamespace(nsOfx); xpath.addNamespace(nsWiki);
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public Ofxdoc correctTemplateInjections(Ofxdoc ofxDoc) throws OfxInternalProcessingException
	{
		Document doc = transformToElement(ofxDoc);
		doc = exchangeParagraphByTemplate(doc);
		
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		return ofxDoc;
	}
	
	private Document exchangeParagraphByTemplate(Document doc)
	{
		try
		{
			Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			
			XPath xpath = XPath.newInstance("//wiki:template");
			xpath.addNamespace(nsOfx);
			xpath.addNamespace(nsWiki);
			
			Element result = exchangeParagraphByTemplate(doc.getRootElement(),xpath);
			result.detach();
			doc.setRootElement(result);
		}
		catch (JDOMException e) {logger.error(e);}
		return doc;
	}
	
	private Element exchangeParagraphByTemplate(Element rootElement, XPath xpath)
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement);
			logger.debug(list.size()+" sections");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element eTemplate = (Element) iter.next();
				int index = eTemplate.getParentElement().getParentElement().indexOf(eTemplate.getParentElement());
				Element parent = eTemplate.getParentElement().getParentElement();
				eTemplate.detach();
				parent.removeContent(index);
				parent.addContent(index, createExternalTemplate(eTemplate));
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return rootElement;
	}
	
	private Element createExternalTemplate(Element eTemplate)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("../");
		sb.append(WikiProcessor.WikiDir.ofxTemplate.toString());
		sb.append("/").append(eTemplate.getAttributeValue("id")).append(".xml");
		
		eTemplate.setAttribute("external", "true");
		eTemplate.setAttribute("source",sb.toString());
		return eTemplate;
	}
	
	private Document transformToElement(Ofxdoc ofxDoc)
	{
		String txt = JaxbUtil.toString(ofxDoc, nsPrefixMapper, true);
		int beginIndex=-1;
		while((beginIndex = txt.indexOf(startDelimiter))>=0)
		{
			String behindStart = txt.substring(beginIndex+startDelimiter.length(), txt.length());
			int endIndex = behindStart.indexOf(endDelimiter);
			String id = behindStart.substring(0,endIndex);
			
			StringBuffer sb = new StringBuffer();
			sb.append(txt.substring(0,beginIndex));
			sb.append(getTemplateXml(id));
			sb.append(txt.substring(beginIndex+startDelimiter.length()+id.length()+endDelimiter.length()));
			txt = sb.toString();
		}
		Document doc = JDomUtil.txtToDoc(txt);
		return doc;
	}
	
	private String getTemplateXml(String id)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<wiki:template id=\"");
		sb.append(id);
		sb.append("\"/>");
		return sb.toString();
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
			
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		String fnOfx = config.getString("wiki.processor.test.template.correct");
		Ofxdoc ofxDoc = (Ofxdoc)JaxbUtil.loadJAXB(fnOfx, Ofxdoc.class);
		
		WikiTemplateCorrector templateCorrector = new WikiTemplateCorrector();
		templateCorrector.correctTemplateInjections(ofxDoc);
		
	}
}