package org.openfuxml.addon.wiki.processor.template.transformator;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;

public interface WikiTemplateTransformator
{
	void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor);
	void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper);
	Element transform(Template template);
}