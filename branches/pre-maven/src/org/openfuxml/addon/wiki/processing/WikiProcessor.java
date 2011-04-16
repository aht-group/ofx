package org.openfuxml.addon.wiki.processing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.ObjectFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.util.WikiContentIO;

@Deprecated
public class WikiProcessor
{
	static Log logger = LogFactory.getLog(WikiProcessor.class);
	private final static String ls = SystemUtils.LINE_SEPARATOR;
	
	public static enum InjectionType {xml,wiki};
	
	private List<Wikireplace> wikiReplaces;
	private List<Wikiinjection> wikiInjectionsXml,wikiInjectionsWiki;
	
	private String wikiText;
	private ObjectFactory of;
	
	private File dirInjection;
	private int injectionId;
	private String article;
	
	public WikiProcessor(Configuration config,String article)
	{
		this.article=article;
		wikiReplaces = new ArrayList<Wikireplace>();
		wikiInjectionsXml = new ArrayList<Wikiinjection>();
		wikiInjectionsWiki = new ArrayList<Wikiinjection>();
		
		of = new ObjectFactory();
		injectionId=1;
		dirInjection = new File(config.getString("/ofx/dir[@type='injection']"));
		
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
		Ofx container=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Ofx.class);
			Unmarshaller u = jc.createUnmarshaller();
			container = (Ofx)u.unmarshal(mrl.searchIs(xmlFile));
			wikiReplaces.addAll(container.getWikireplace());
			for(Wikiinjection wikiInjection : container.getWikiinjection())
			{
				switch (InjectionType.valueOf(wikiInjection.getFormat()))
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
		while(wikiText.indexOf("<"+inject.getWikitag()+">")>0)
		{
			inject.setId(""+injectionId);injectionId++;
			inject.setArticle(article);
			
			StringBuffer sbDebug = new StringBuffer();
			String startTag = "<"+inject.getWikitag()+">";
			int from = wikiText.indexOf(startTag);
			int to = wikiText.indexOf("</"+inject.getWikitag()+">");
			
			StringBuffer injectionSb = WikiContentIO.toString(inject);
			logger.debug(injectionSb);
			inject.setWikicontent(of.createWikiinjectionWikicontent());
			inject.getWikicontent().setValue(wikiText.substring(from+startTag.length(), to));
			
			WikiContentIO.toFile(inject,dirInjection);
			
			sbDebug.append("Injection: "+from+" "+to);
			sbDebug.append(" oldSize="+wikiText.length());
			StringBuffer sb = new StringBuffer();
				sb.append(wikiText.substring(0, from-1));
				if(inject.getOfxtag()!=null && inject.getOfxtag().length()>0)
				{
					
					sb.append(injectionSb);
					sb.append(ls);
				}
				sb.append(wikiText.substring(to+inject.getWikitag().length()+3+1,wikiText.length()));
			wikiText=sb.toString();
			sbDebug.append(" newSize="+wikiText.length());
			logger.debug(sbDebug);
		}
	}
}