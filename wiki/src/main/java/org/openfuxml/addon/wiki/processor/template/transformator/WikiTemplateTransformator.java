package org.openfuxml.addon.wiki.processor.template.transformator;

import org.exlp.interfaces.io.NsPrefixMapperInterface;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.processor.markup.WikiInlineProcessor;
import org.openfuxml.model.xml.addon.wiki.Template;

public interface WikiTemplateTransformator
{
	void setWikiInlineProcessor(WikiInlineProcessor wikiInlineProcessor);
	void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper);
	Element transform(Template template);
}