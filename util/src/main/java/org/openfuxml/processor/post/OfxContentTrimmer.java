package org.openfuxml.processor.post;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxContentTrimmer
{
	final static Logger logger = LoggerFactory.getLogger(OfxContentTrimmer.class);
	
	private List<XPathExpression<Element>> xpaths;
	
	public OfxContentTrimmer()
	{
		List<Namespace> ns = OfxNsPrefixMapper.toOfxNamespaces();
		
		xpaths.add(XPathFactory.instance().compile("//ofx:paragraph", Filters.element(), null, ns));
		
	}
	
	public Document trim(Document doc) throws OfxInternalProcessingException
	{		
		for(XPathExpression<Element> xpe : xpaths)
		{
			Element result = mergeRecursive(doc.getRootElement(),xpe);
			result.detach();
			doc.setRootElement(result);
		}
		return doc;
	}
	
	private Element mergeRecursive(Element rootElement, XPathExpression<Element> xpe) throws OfxInternalProcessingException
	{
		List<Element> list2 = xpe.evaluate(rootElement);
		logger.debug(list2.size()+" sections");
		
		for (Element e : list2)
		{
			boolean noChilds = (e.getChildren().size()==0);
			boolean noContent = (e.getText().length()==0);
			logger.trace(e.getName()+" "+e.getChildren().size()+" "+e.getText().length());
			if(noChilds && noContent){e.detach();}
			else{e.setText(e.getTextTrim());}
		}
		return rootElement;
	}
}