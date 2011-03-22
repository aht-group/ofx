package org.openfuxml.renderer.processor.pre;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.content.ofx.Ofxdoc;

public class OfxContainerMerger
{
	static Log logger = LogFactory.getLog(OfxContainerMerger.class);
	
	private XPath xpathSections;
	
	public OfxContainerMerger()
	{
		try
		{
			xpathSections = XPath.newInstance("//ofx:sections");
			xpathSections.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			xpathSections.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public Ofxdoc merge(Ofxdoc ofxDoc)
	{
		Document doc = JaxbUtil.toDocument(ofxDoc);

		Element result = mergeRecursive(doc.getRootElement());
		result.detach();
		doc.setRootElement(result);
		
		ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		return ofxDoc;
	}
	
	private Element mergeRecursive(Element rootElement)
	{
		try
		{
			List<?> list = xpathSections.selectNodes(rootElement);
			logger.debug(list.size()+" sections");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element sectionsElement = (Element) iter.next();
				
				int index = sectionsElement.getParentElement().indexOf(sectionsElement);
				List<Element> lSection = new ArrayList<Element>();
				for(Object o : sectionsElement.getChildren())
				{
					Element eSection = (Element)o;					
					lSection.add(eSection);
				}
				for(Element e : lSection){e.detach();}
				sectionsElement.getParentElement().addContent(index, lSection);
				
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return rootElement;
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		String fName = "resources/data/xml/preprocessor/sections.xml";
		if(args.length == 1 ){fName = args[0];}
		
		Ofxdoc ofxDocOriginal = (Ofxdoc)JaxbUtil.loadJAXB(fName, Ofxdoc.class);
		JaxbUtil.debug(ofxDocOriginal);
		
		OfxContainerMerger containerMerger = new OfxContainerMerger();
		Ofxdoc ofxDocContainer = containerMerger.merge(ofxDocOriginal);
		JaxbUtil.debug(ofxDocContainer);
	}
}