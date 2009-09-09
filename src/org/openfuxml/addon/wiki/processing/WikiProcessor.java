package org.openfuxml.addon.wiki.processing;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.util.WikiContentIO;

public class WikiProcessor
{
	static Logger logger = Logger.getLogger(WikiProcessor.class);
	public static enum InjectionType {xml,wiki};
	
	private List<Wikireplace> wikiReplaces;
	private List<Wikiinjection> wikiInjectionsXml,wikiInjectionsWiki;
	
	private String wikiText;
	
	public WikiProcessor(Configuration config)
	{
		wikiReplaces = new ArrayList<Wikireplace>();
		wikiInjectionsXml = new ArrayList<Wikiinjection>();
		wikiInjectionsWiki = new ArrayList<Wikiinjection>();
		
		MultiResourceLoader mrl = new MultiResourceLoader();
		int numberTranslations = config.getStringArray("wikiprocessor/file").length;
		for(int i=1;i<=numberTranslations;i++)
		{
			String xmlFile = config.getString("wikiprocessor/file["+i+"]");
			loadWikiContainer(xmlFile,mrl);
		}
		logger.debug("Replacements loaded: "+wikiReplaces.size());
		logger.debug("Injections loaded: xml="+wikiInjectionsXml.size()+" wiki="+wikiInjectionsWiki.size());
	}
	
	private void loadWikiContainer(String xmlFile,MultiResourceLoader mrl)
	{
		Wikicontainer container=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Wikicontainer.class);
			Unmarshaller u = jc.createUnmarshaller();
			container = (Wikicontainer)u.unmarshal(mrl.searchIs(xmlFile));
			wikiReplaces.addAll(container.getWikireplace());
			for(Wikiinjection wikiInjection : container.getWikiinjection())
			{
				switch (InjectionType.valueOf(wikiInjection.getType()))
				{
					case xml: 	wikiInjectionsXml.add(wikiInjection);break;
					case wiki:	wikiInjectionsWiki.add(wikiInjection);break;
				}
			}
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public String process(String wikiText)
	{
		this.wikiText=wikiText;
		for(Wikireplace replace : wikiReplaces){wikiReplace(replace);}
		for(Wikiinjection inject : wikiInjectionsXml){wikiInject(inject);}
		return this.wikiText;
	}

	private void wikiReplace(Wikireplace replace)
	{
		wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
	}
	
	private void wikiInject(Wikiinjection inject)
	{
		while(wikiText.indexOf("<"+inject.getTag()+">")>0)
		{
			StringBuffer sbDebug = new StringBuffer();
			String startTag = "<"+inject.getTag()+">";
			int from = wikiText.indexOf(startTag);
			int to = wikiText.indexOf("</"+inject.getTag()+">");
			
			StringBuffer injectionSb = WikiContentIO.toString(inject);
			logger.debug(injectionSb);
			inject.setValue(wikiText.substring(from+startTag.length(), to));
			WikiContentIO.toFile(inject);
			
			sbDebug.append("Injection: "+from+" "+to);
			sbDebug.append(" oldSize="+wikiText.length());
			StringBuffer sb = new StringBuffer();
				sb.append(wikiText.substring(0, from-1));
				if(inject.getId()!=null && inject.getId().length()>0){sb.append(injectionSb);}
				sb.append(wikiText.substring(to+inject.getTag().length()+3+1,wikiText.length()));
			wikiText=sb.toString();
			sbDebug.append(" newSize="+wikiText.length());
			logger.debug(sbDebug);
		}
	}
}