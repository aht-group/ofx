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
	
	private String wikiText;
	
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
		this.wikiText=wikiText;
		for(Wikireplace replace : wikiReplaces){wikiReplace(replace);}
		for(Wikiinjection inject : wikiInjections){wikiInject(inject);}
		return this.wikiText;
	}

	private void wikiReplace(Wikireplace replace)
	{
		wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
	}
	
	private void wikiInject(Wikiinjection inject)
	{
		while(wikiText.indexOf(inject.getFrom())>0)
		{
			StringBuffer sbDebug = new StringBuffer();
			int from = wikiText.indexOf(inject.getFrom());
			int to = wikiText.indexOf(inject.getTo());
			sbDebug.append("Injection: "+from+" "+to);
			sbDebug.append(" oldSize="+wikiText.length());
			String injectionArea = wikiText.substring(from, to+inject.getTo().length());
			StringBuffer sb = new StringBuffer();
				sb.append(wikiText.substring(0, from-1));
				sb.append(wikiText.substring(to+inject.getTo().length()+1,wikiText.length()));
			wikiText=sb.toString();
			sbDebug.append(" newSize="+wikiText.length());
			logger.debug(sbDebug);
		}
	}
}
