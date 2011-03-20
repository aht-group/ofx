package org.openfuxml.addon.wiki.processor.util;

import java.io.FileNotFoundException;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class WikiConfigXmlSourceLoader
{
	static Log logger = LogFactory.getLog(WikiConfigXmlSourceLoader.class);
	
	public static synchronized Replacements initReplacements(Replacements replacements) throws OfxConfigurationException
	{
		if(replacements.isSetExternal() && replacements.isExternal())
		{
			try
			{
				if(replacements.isSetSource())
				{
					replacements = (Replacements)JaxbUtil.loadJAXB(replacements.getSource(), Replacements.class);
				}
				else {throw new OfxConfigurationException("Replacement is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				//TODO nested exception
				throw new OfxConfigurationException(e.getMessage());
			}
		}
//		JaxbUtil.debug(replacements);
		return replacements;
	}
	
	public static synchronized  Injections initInjections(Injections injections) throws OfxConfigurationException
	{
		if(injections.isSetExternal() && injections.isExternal())
		{
			try
			{
				if(injections.isSetSource())
				{
					injections = (Injections)JaxbUtil.loadJAXB(injections.getSource(), Injections.class);
				}
				else {throw new OfxConfigurationException("Replacement is set to external, but no source definded");}
			}
			catch (FileNotFoundException e)
			{
				//TODO nested exception
				throw new OfxConfigurationException(e.getMessage());
			}
		}
//		JaxbUtil.debug(injections);
		return injections;
	}
	
}