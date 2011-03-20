package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.ObjectFactory;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlSourceLoader;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class WikiMarkupProcessor
{
	static Log logger = LogFactory.getLog(WikiMarkupProcessor.class);
	private final static String ls = SystemUtils.LINE_SEPARATOR;
	
	public static enum InjectionType {xml,wiki};
	
	private Replacements replacements;
	private Injections injections;
	private File wikiPlainDir,wikiMarkupDir;
	
	private String wikiText;
	private ObjectFactory of;
	
	@Deprecated
	private File dirInjection;
	private int injectionId;
	
	public WikiMarkupProcessor(Replacements replacements, Injections injections) throws OfxConfigurationException
	{
		this.replacements = WikiConfigXmlSourceLoader.initReplacements(replacements);
		this.injections = WikiConfigXmlSourceLoader.initInjections(injections);;
	}
	
	public void setDirectories(File wikiPlainDir, File wikiMarkupDir)
	{
		this.wikiPlainDir=wikiPlainDir;
		this.wikiMarkupDir=wikiMarkupDir;
		logger.debug("Directory Plain:  "+wikiPlainDir.getAbsolutePath());
		logger.debug("Directory Markup: "+wikiMarkupDir.getAbsolutePath());
	}
	
	public void process(List<Content> lContent)
	{
		for(Content content : lContent)
		{
			String fName = org.openfuxml.addon.wiki.processor.util.WikiContentIO.getFileFromSource(content.getSource(), "txt");
			String txt = org.openfuxml.addon.wiki.processor.util.WikiContentIO.loadTxt(wikiPlainDir, fName);
			String result = process(txt, "article ... req?");
			org.openfuxml.addon.wiki.processor.util.WikiContentIO.writeTxt(wikiMarkupDir, fName, result);
		}
	}
	
	public String process(String wikiText, String article)
	{
		this.wikiText=wikiText;
		for(Wikireplace replace : replacements.getWikireplace()){processReplacements(replace);}
		for(Wikiinjection inject : injections.getWikiinjection()){wikiInject(inject,article);}
		return this.wikiText;
	}

	private void processReplacements(Wikireplace replace)
	{
		wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
	}
	
	private void wikiInject(Wikiinjection inject, String article)
	{
		while(wikiText.indexOf("<"+inject.getWikitag()+">")>0)
		{
			logger.warn("Injection should start, but currentl NYI");
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