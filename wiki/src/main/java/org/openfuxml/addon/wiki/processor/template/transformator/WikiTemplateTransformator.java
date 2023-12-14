package org.openfuxml.addon.wiki.processor.template.transformator;

import org.jdom2.Element;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;

import net.sf.exlp.xml.ns.NsPrefixMapperInterface;

public interface WikiTemplateTransformator
{
	void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor);
	void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper);
	Element transform(Template template);
}