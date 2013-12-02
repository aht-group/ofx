package org.openfuxml.util;

import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxMultilangFilter
{
	final static Logger logger = LoggerFactory.getLogger(OfxMultilangFilter.class);

	public OfxMultilangFilter()
	{
		
		
	}
	
	public Document merge(Document ofxDoc) throws OfxInternalProcessingException
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(ofxDoc);

		XPathFactory xpfac = XPathFactory.instance();
		XPathExpression<Element> xp = xpfac.compile("//target/@lang/..", Filters.element());
		for (Element att : xp.evaluate(j2Doc)) {
		  System.out.println("We have target " + att.getValue());
		}
		
		return ofxDoc;
	}
}