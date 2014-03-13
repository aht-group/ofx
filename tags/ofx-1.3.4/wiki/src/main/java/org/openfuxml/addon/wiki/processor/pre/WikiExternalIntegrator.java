package org.openfuxml.addon.wiki.processor.pre;

import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.xpath.XPath;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiExternalIntegrator
{
	final static Logger logger = LoggerFactory.getLogger(WikiExternalIntegrator.class);
	
	private Namespace ns;
	private XPath xpath;
	
	private int counter;
	private String wikiXmlDirName;
	
	private Document ofxDocWithWikisAsExternal;
	private Contents wikiQueries;
	
	public WikiExternalIntegrator(String wikiXmlDirName)
	{
		this.wikiXmlDirName=wikiXmlDirName;
		try
		{
			ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			ns = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			xpath = XPath.newInstance("//wiki:content");
			xpath.addNamespace(ns);
		}
		catch (JDOMException e) {logger.error("",e);}
		xpath.addNamespace(ns);
		counter = 1;
		wikiQueries = new Contents();
	}
	
	public void integrateWikiAsExternal(Document ofxDoc) throws OfxAuthoringException
	{
		org.jdom2.Document doc = JaxbUtil.toDocument(ofxDoc);
		
		try
		{
			List<?> list = xpath.selectNodes(doc.getRootElement());
			logger.debug(list.size()+" <wiki:content/> found");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element eChild = (Element) iter.next();
				
				logger.trace(eChild.getName());
				Content wikiContent = (Content)JDomUtil.toJaxb(eChild, Content.class);
				
				Element eOfx = processWikiContent(wikiContent);
				wikiContent.setSource(eOfx.getAttributeValue("source"));
				wikiQueries.getContent().add(wikiContent);
				
				int index = eChild.getParentElement().indexOf(eChild);
				eChild.getParentElement().addContent(index,eOfx);
				eChild.detach();
			}
		}
		catch (JDOMException e) {logger.error("",e);}

		ofxDocWithWikisAsExternal = (Document)JDomUtil.toJaxb(doc, Document.class);
	}
	
	public Document getResult() {return ofxDocWithWikisAsExternal;}
	public Contents getWikiQueries() {return wikiQueries;}
	
	
	private Element processWikiContent(Content wikiContent) throws OfxAuthoringException
	{
		Element e=null;
		if     (wikiContent.isSetPage()){e=getSection(wikiContent);}
		else if(wikiContent.isSetCategory()){e=getCategory(wikiContent);}
		else {throw new OfxAuthoringException("Element wiki:content has no known child");}
		return e;
	}
	
	private Element getCategory(Content wikiContent)
	{
		org.openfuxml.content.ofx.Sections ofxSections = new org.openfuxml.content.ofx.Sections();
		ofxSections.setExternal(true);
		ofxSections.setSource(wikiXmlDirName+"/"+counter+".xml");counter++;
		Element eResult = JaxbUtil.toDocument(ofxSections).getRootElement();
		eResult.detach();
		return eResult;
	}
	
	private Element getSection(Content wikiContent)
	{
		org.openfuxml.content.ofx.Section ofxSection = new org.openfuxml.content.ofx.Section();
		ofxSection.setExternal(true);
		ofxSection.setSource(wikiXmlDirName+"/"+counter+".xml");counter++;
		Element eResult = JaxbUtil.toDocument(ofxSection).getRootElement();
		eResult.detach();
		return eResult;
	}
}