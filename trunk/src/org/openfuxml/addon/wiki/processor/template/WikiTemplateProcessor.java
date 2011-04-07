package org.openfuxml.addon.wiki.processor.template;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.addon.wiki.processor.template.transformator.WikiTemplateKeyValueTransformator;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlXpathHelper;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;

public class WikiTemplateProcessor extends AbstractWikiProcessor
{
	static Log logger = LogFactory.getLog(WikiTemplateProcessor.class);
	
	private Templates templates;
	
	private WikiTemplateKeyValueTransformator kvTransformator;
	
	public WikiTemplateProcessor(Templates templates) 
	{
		this.templates=templates;
		kvTransformator = new WikiTemplateKeyValueTransformator();
	}
	
	public void process() throws OfxInternalProcessingException, OfxConfigurationException
	{
		File fTemplateDir = getDir(WikiProcessor.WikiDir.wikiTemplate);
		for(File fTemplate : fTemplateDir.listFiles())
		{
			try
			{
				Template template = (Template)JaxbUtil.loadJAXB(fTemplate.getAbsolutePath(), Template.class);
				File fOfxTemplate = new File(getDir(WikiProcessor.WikiDir.ofxTemplate),template.getId()+".xml");
				Document doc = processTemplate(template);
				JDomUtil.save(doc, fOfxTemplate, Format.getRawFormat());
			}
			catch (FileNotFoundException e)
			{
				throw new OfxInternalProcessingException(e.getMessage());
			}
		}
	}
	
	private Document processTemplate(Template template) throws OfxConfigurationException
	{
		Document doc = new Document();
		
		Template templateDef = WikiConfigXmlXpathHelper.getTemplate(templates, template.getName());
		Element e = kvTransformator.transform(templateDef,template);
		doc.setRootElement(e);
		return doc;
	}
	
}