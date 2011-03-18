package org.openfuxml.addon.wiki.processor.pre;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.content.ofx.Ofxdoc;

public class WikiExternalIntegrator
{
	static Log logger = LogFactory.getLog(WikiExternalIntegrator.class);
	
	private Namespace ns;
	private XPath xpath;
	
	private int counter;
	private String subDir;
	
	private Ofxdoc ofxDocWithWikisAsExternal;
	private List<Content> wikiQueries;
	
	public WikiExternalIntegrator(String subDir)
	{
		this.subDir=subDir;
		try
		{
			ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			ns = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			xpath = XPath.newInstance("//wiki:content");
			xpath.addNamespace(ns);
		}
		catch (JDOMException e) {logger.error(e);}
		xpath.addNamespace(ns);
		counter = 1;
		wikiQueries = new ArrayList<Content>();
	}
	
	public void integrateWikiAsExternal(Ofxdoc ofxDoc)
	{
		Document doc = JaxbUtil.toDocument(ofxDoc);
		
		try
		{
			List<?> list = xpath.selectNodes(doc.getRootElement());
			logger.debug(list.size()+" <wiki:content/> found");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element eChild = (Element) iter.next();
				
				logger.debug(eChild.getName());
				Content wikiContent = (Content)JDomUtil.toJaxb(eChild, Content.class);
				
				Element eOfx = processWikiContent(wikiContent);
				wikiContent.setSource(eOfx.getAttributeValue("source"));
				wikiQueries.add(wikiContent);
				
				int index = eChild.getParentElement().indexOf(eChild);
				eChild.getParentElement().addContent(index,eOfx);
				eChild.detach();
			}
		}
		catch (JDOMException e) {logger.error(e);}

		ofxDocWithWikisAsExternal = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
	}
	
	public Ofxdoc getResult() {return ofxDocWithWikisAsExternal;}
	public List<Content> getWikiQueries() {return wikiQueries;}
	
	
	private Element processWikiContent(Content wikiContent)
	{
		Element e=null;
		if(wikiContent.isSetPage()){e=getSection(wikiContent.getPage());}
		return e;
	}
	
	private Element getSection(Page section)
	{
		org.openfuxml.content.ofx.Section ofxSection = new org.openfuxml.content.ofx.Section();
		ofxSection.setExternal(true);
		ofxSection.setSource(subDir+"/"+counter+".xml");counter++;
		Element eResult = JaxbUtil.toDocument(ofxSection).getRootElement();
		eResult.detach();
		return eResult;
		
	}
}