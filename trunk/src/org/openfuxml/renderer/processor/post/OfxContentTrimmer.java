package org.openfuxml.renderer.processor.post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;

public class OfxContentTrimmer
{
	static Log logger = LogFactory.getLog(OfxContentTrimmer.class);
	
	private List<XPath> lXpath;
	
	
	public OfxContentTrimmer()
	{
		lXpath = new ArrayList<XPath>();
		try
		{
			Namespace nsOfx = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			Namespace nsWiki = Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki");
			
			XPath xpSections  = XPath.newInstance("//ofx:paragraph");
			xpSections.addNamespace(nsOfx); xpSections.addNamespace(nsWiki);
			lXpath.add(xpSections);
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public Document trim(Document doc) throws OfxInternalProcessingException
	{
		for(XPath xpath : lXpath)
		{
			Element result = mergeRecursive(doc.getRootElement(),xpath);
			result.detach();
			doc.setRootElement(result);
		}
		return doc;
	}
	
	private Element mergeRecursive(Element rootElement, XPath xpath) throws OfxInternalProcessingException
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement);
			logger.debug(list.size()+" sections");
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element e = (Element) iter.next();
				e.setText(e.getTextTrim());
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return rootElement;
	}
}