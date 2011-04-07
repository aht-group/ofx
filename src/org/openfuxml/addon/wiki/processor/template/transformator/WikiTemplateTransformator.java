package org.openfuxml.addon.wiki.processor.template.transformator;

import net.sf.exlp.util.xml.NsPrefixMapperInterface;

import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.Template;

public interface WikiTemplateTransformator
{
	void setNsPrefixMapperInterface(NsPrefixMapperInterface nsPrefixMapper);
	Element transform(Template template);
}