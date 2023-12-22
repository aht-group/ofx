package org.openfuxml.util.filter;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.jdom2.Attribute;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxClassifierFilter
{
	final static Logger logger = LoggerFactory.getLogger(OfxClassifierFilter.class);

	private String[] classifiers;
	
	public OfxClassifierFilter(String... classifiers)
	{
		this.classifiers=classifiers;
	}
	
	public Section filterLang(Section section)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(section);
		filterLang(j2Doc);
		return (Section)JDomUtil.toJaxb(j2Doc, Section.class);
	}
	
	public void filterLang(Document ofxDocument)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(ofxDocument);
		filterLang(j2Doc);
	}
	
	private void filterLang(org.jdom2.Document j2Doc)
	{
		XPathFactory xpfac = XPathFactory.instance();
		XPathExpression<Attribute> xp = xpfac.compile("//*/@classifier", Filters.attribute());
		for (Attribute att : xp.evaluate(j2Doc))
		{
			boolean remove = true;
			for(String classifier : classifiers)
			{
				if(att.getValue().equals(classifier))
				{
					remove = false;
				}
			}
			if(remove){att.getParent().detach();}
		}
	}
}