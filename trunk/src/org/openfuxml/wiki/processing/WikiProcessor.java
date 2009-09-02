package org.openfuxml.wiki.processing;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.wiki.data.jaxb.Wikireplace;

public class WikiProcessor
{
	static Logger logger = Logger.getLogger(WikiProcessor.class);
	
	private List<Wikireplace> wikiReplaces;
	private List<Wikiinjection> wikiInjections;
	
	public WikiProcessor(Configuration config)
	{
		wikiReplaces = new ArrayList<Wikireplace>();
		wikiInjections = new ArrayList<Wikiinjection>();
		
		MultiResourceLoader mrl = new MultiResourceLoader();
		int numberTranslations = config.getStringArray("wikiprocessor/file").length;
		for(int i=1;i<=numberTranslations;i++)
		{
			String xmlFile = config.getString("wikiprocessor/file["+i+"]");
			loadReplacements(xmlFile,mrl);
		}
		logger.debug("Replacements loaded: "+wikiReplaces.size());
		logger.debug("Injections loaded: "+wikiInjections.size());
	}
	
	private void loadReplacements(String xmlFile,MultiResourceLoader mrl)
	{
		Wikicontainer container=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Wikicontainer.class);
			Unmarshaller u = jc.createUnmarshaller();
			container = (Wikicontainer)u.unmarshal(mrl.searchIs(xmlFile));
			wikiReplaces.addAll(container.getWikireplace());
			wikiInjections.addAll(container.getWikiinjection());
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public String process(String wikiText)
	{
		for(Wikireplace replace : wikiReplaces)
		{
			wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
		}
		return wikiText;
	}

}
