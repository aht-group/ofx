package org.openfuxml.addon.wiki.processor.util;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Template;
import org.openfuxml.addon.wiki.data.jaxb.Templates;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

public class WikiConfigXmlXpathHelper
{
	final static Logger logger = LoggerFactory.getLogger(WikiConfigXmlXpathHelper.class);
	
	public static synchronized Template getTemplate(Templates templates, String name) throws OfxConfigurationException 
	{
		Template result = new Template();
		List<Namespace> ns = OfxNsPrefixMapper.toOfxNamespaces();
		XPathExpression<Element> xpe = XPathFactory.instance().compile("//wiki:template[@name='"+name+"']", Filters.element(), null, ns);

		
		Document doc = JaxbUtil.toDocument(templates);
		Element e =  xpe.evaluateFirst(doc);
		if(e!=null){result = (Template)JDomUtil.toJaxb(e, Template.class);}
		else{throw new OfxConfigurationException("No template definition for templateName="+name);}
        return result;
	}
	
	public static synchronized Replacements initReplacements(Replacements replacements) throws OfxConfigurationException
	{
		if(Objects.nonNull(replacements.isExternal()) && replacements.isExternal())
		{
			try
			{
				if(Objects.nonNull(replacements.getSource()))
				{
					replacements = (Replacements)JaxbUtil.loadJAXB(replacements.getSource(), Replacements.class);
				}
				else {throw new OfxConfigurationException("Replacement is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				throw new OfxConfigurationException(e.getMessage());
			}
		}
		return replacements;
	}
	
	public static synchronized  Injections initInjections(Injections injections) throws OfxConfigurationException
	{
		if(Objects.nonNull(injections.isExternal()) && injections.isExternal())
		{
			try
			{
				if(Objects.nonNull(injections.getSource()))
				{
					logger.debug("Loading external "+Injections.class.getSimpleName()+" file: "+injections.getSource());
					injections = (Injections)JaxbUtil.loadJAXB(injections.getSource(), Injections.class);
				}
				else {throw new OfxConfigurationException(Injections.class.getSimpleName()+" is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				//TODO nested exception
				throw new OfxConfigurationException(e.getMessage());
			}
		}
		logger.debug(JaxbUtil.toString(injections));
		return injections;
	}
	
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.path("resources/config");
			loggerInit.init();
		
		String fnInjections = "resources/config/wiki/wikiinjection.xml";
			
		Injections injections = (Injections)JaxbUtil.loadJAXB(fnInjections, Injections.class);
		
		logger.debug(JaxbUtil.toString(injections));
	}
}