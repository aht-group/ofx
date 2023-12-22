package org.openfuxml.addon.wiki.processor.template;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.core.ofx.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class WikiTemplateCorrector extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiTemplateCorrector.class);
	
	private final String startDelimiter = "&lt;wiki:injection id=&quot;";
	private final String endDelimiter = "&quot;/&gt;";
	
	public WikiTemplateCorrector() 
	{
		
	}
	
	public Document correctTemplateInjections(Document ofxDoc) throws OfxInternalProcessingException
	{
		org.jdom2.Document doc = transformToElement(ofxDoc);
		doc = exchangeParagraphByTemplate(doc);
		
		ofxDoc = (Document)JDomUtil.toJaxb(doc, Document.class);
		return ofxDoc;
	}
	
	private org.jdom2.Document exchangeParagraphByTemplate(org.jdom2.Document doc)
	{
		Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
		Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
		
		XPathExpression<Element> xpe = XPathFactory.instance().compile("//wiki:template",Filters.element(), null,nsOfx,nsWiki);
		
		Element result = exchangeParagraphByTemplate(doc.getRootElement(),xpe);
		result.detach();
		doc.setRootElement(result);
		
		return doc;
	}
	
	private Element exchangeParagraphByTemplate(Element rootElement, XPathExpression<Element> xpe)
	{
		List<Element> list = xpe.evaluate(rootElement);
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
		return rootElement;
	}
	
	private Element createExternalTemplate(Element eTemplate)
	{
		StringBuffer sb = new StringBuffer();
//		sb.append("../");
		sb.append(WikiProcessor.WikiDir.ofxTemplate.toString());
		sb.append("/").append(eTemplate.getAttributeValue("id")).append(".xml");
		
		eTemplate.setAttribute("external", "true");
		eTemplate.setAttribute("source",sb.toString());
		return eTemplate;
	}
	
	private org.jdom2.Document transformToElement(Document ofxDoc)
	{
		String txt = JaxbUtil.toString(ofxDoc,true);
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
		org.jdom2.Document doc = null;
		try {doc = JDomUtil.txtToDoc(txt);}
		catch (JDOMException e) {logger.error("",e);}
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
}