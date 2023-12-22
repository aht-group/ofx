package org.openfuxml.addon.wiki.processor.pre;

import java.util.List;
import java.util.Objects;

import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.model.xml.addon.wiki.Content;
import org.openfuxml.model.xml.addon.wiki.Contents;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class WikiExternalIntegrator
{
	final static Logger logger = LoggerFactory.getLogger(WikiExternalIntegrator.class);
	
	private XPathExpression<Element> xpe;
	
	private int counter;
	private String wikiXmlDirName;
	
	private Document ofxDocWithWikisAsExternal;
	private Contents wikiQueries;
	
	public WikiExternalIntegrator(String wikiXmlDirName)
	{
		List<Namespace> ns = OfxNsPrefixMapper.toOfxNamespaces();
		xpe = XPathFactory.instance().compile("//wiki:content", Filters.element(), null, ns);
		
		
		counter = 1;
		wikiQueries = new Contents();
	}
	
	public void integrateWikiAsExternal(Document ofxDoc) throws OfxAuthoringException
	{
		org.jdom2.Document doc = JaxbUtil.toDocument(ofxDoc);
		
		List<Element> list2 = xpe.evaluate(doc.getRootElement());
		logger.debug(list2.size()+" sections");
		
		for (Element eChild : list2)
		{
			
			logger.trace(eChild.getName());
			Content wikiContent = (Content)JDomUtil.toJaxb(eChild, Content.class);
			
			Element eOfx = processWikiContent(wikiContent);
			wikiContent.setSource(eOfx.getAttributeValue("source"));
			wikiQueries.getContent().add(wikiContent);
			
			int index = eChild.getParentElement().indexOf(eChild);
			eChild.getParentElement().addContent(index,eOfx);
			eChild.detach();
		}
		
		ofxDocWithWikisAsExternal = (Document)JDomUtil.toJaxb(doc, Document.class);
	}
	
	public Document getResult() {return ofxDocWithWikisAsExternal;}
	public Contents getWikiQueries() {return wikiQueries;}
	
	
	private Element processWikiContent(Content wikiContent) throws OfxAuthoringException
	{
		Element e=null;
		if     (Objects.nonNull(wikiContent.getPage())) {e=getSection(wikiContent);}
		else if(Objects.nonNull(wikiContent.getCategory())) {e=getCategory(wikiContent);}
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