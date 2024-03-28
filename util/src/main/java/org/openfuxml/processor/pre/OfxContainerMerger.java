package org.openfuxml.processor.pre;

import java.util.ArrayList;
import java.util.List;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.ofx.Sections;
import org.openfuxml.model.xml.core.ofx.Title;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class OfxContainerMerger
{
	final static Logger logger = LoggerFactory.getLogger(OfxContainerMerger.class);
	
	private List<XPathExpression<Element>> xpaths;
	
	public OfxContainerMerger()
	{
		List<Namespace> ns = new ArrayList<>();
		ns.add(OfxNsPrefixMapper.nsOfx);
		
		xpaths = new ArrayList<>();
		xpaths.add(XPathFactory.instance().compile("//ofx:sections", Filters.element(), null, ns));
		xpaths.add(XPathFactory.instance().compile("//ofx:section[@container='true']", Filters.element(), null, ns));
	}
	
	public Document merge(Document ofxDoc) throws OfxInternalProcessingException
	{
		org.jdom2.Document doc = JaxbUtil.toDocument(ofxDoc);

		for(XPathExpression<Element> xpe : xpaths)
		{
			Element result = mergeRecursive(doc.getRootElement(),xpe);
			result.detach();
			doc.setRootElement(result);
		}
		
		ofxDoc = (Document)JDomUtil.toJaxb(doc,Document.class);
		return ofxDoc;
	}
	
	private Element mergeRecursive(Element rootElement, XPathExpression<Element> xpe) throws OfxInternalProcessingException
	{
//		try
		{
			List<Element> list2 = xpe.evaluate(rootElement);
			logger.debug(list2.size()+" sections");
			
			for (Element e : list2)
			{
				int index = e.getParentElement().indexOf(e);
				List<Element> lChilds = new ArrayList<Element>();
				
				if     (e.getName().equalsIgnoreCase(Sections.class.getSimpleName())){lChilds = processSections(e.getChildren());}
				else if(e.getName().equalsIgnoreCase(Section.class.getSimpleName())){lChilds = processSection(e.getChildren());}
				else {throw new OfxInternalProcessingException("Root element <"+e.getName()+"> of Wiki.Processing not expected");}
				
				e.getParentElement().addContent(index, lChilds);
				e.getParentElement().removeContent(e);
			}
		}
//		catch (JDOMException e) {logger.error("",e);}
		return rootElement;
	}
	
	private List<Element> processSections(List<?> lChilds)
	{
		List<Element> lSection = new ArrayList<Element>();
		for(Object o : lChilds)
		{
			Element e = (Element)o;				
			lSection.add(e);
		}
		for(Element e : lSection){e.detach();}
		return lSection;
	}
	
	private List<Element> processSection(List<?> lChilds)
	{
		List<Element> lSection = new ArrayList<Element>();
		for(Object o : lChilds)
		{
			Element e = (Element)o;
			boolean add = true;
			if(e.getName().equalsIgnoreCase(Title.class.getSimpleName())){add=false;}
			if(add){lSection.add(e);}
		}
		for(Element e : lSection){e.detach();}
		return lSection;
	}
}