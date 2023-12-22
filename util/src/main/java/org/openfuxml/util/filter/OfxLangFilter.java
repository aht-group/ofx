package org.openfuxml.util.filter;

import org.jdom2.Attribute;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.model.xml.core.editorial.Acronyms;
import org.openfuxml.model.xml.core.editorial.Glossary;
import org.openfuxml.model.xml.core.editorial.Index;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.model.xml.core.table.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class OfxLangFilter
{
	final static Logger logger = LoggerFactory.getLogger(OfxLangFilter.class);

	private String lang;
	
	public OfxLangFilter(String lang)
	{
		this.lang=lang;
	}
	
	public Section filterLang(Section section)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(section);
		filterLang(j2Doc);
		return JDomUtil.toJaxb(j2Doc, Section.class);
	}
	
	public Table filterLang(Table table)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(table);
		filterLang(j2Doc);
		return JDomUtil.toJaxb(j2Doc, Table.class);
	}
	
	public Glossary filterLang(Glossary glossary)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(glossary);
		filterLang(j2Doc);
		return JDomUtil.toJaxb(j2Doc, Glossary.class);
	}
	
	public Acronyms filterLang(Acronyms acronyms)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(acronyms);
		filterLang(j2Doc);
		return JDomUtil.toJaxb(j2Doc, Acronyms.class);
	}
	
	public void filterLang(Document ofxDocument)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(ofxDocument);
		filterLang(j2Doc);
	}
	
	private void filterLang(org.jdom2.Document j2Doc)
	{
		XPathFactory xpfac = XPathFactory.instance();
		XPathExpression<Attribute> xp = xpfac.compile("//*/@lang", Filters.attribute());
		for (Attribute att : xp.evaluate(j2Doc))
		{
			if(!att.getValue().equals(lang))
			{
				att.getParent().detach();
			}
		}
	}

	public Index filterLang(Index index)
	{
		org.jdom2.Document j2Doc = JaxbUtil.toDocument(index);
		filterLang(j2Doc);
		return JDomUtil.toJaxb(j2Doc, Index.class);
	}
}