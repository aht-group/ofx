package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;

import org.apache.commons.lang.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.exception.OfxWikiException;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.data.jaxb.Injections;
import org.openfuxml.addon.wiki.data.jaxb.ObjectFactory;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processor.net.WikiContentFetcher;
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
	
	public void process(Contents wikiQueries) throws OfxWikiException
	{
		for(Content content : wikiQueries.getContent())
		{
			if(content.isSetPage()){processPage(content.getPage());}
			else if(content.isSetCategory()){processCategory(content.getCategory());}
			else {throw new OfxWikiException("No "+WikiMarkupProcessor.class.getSimpleName()+" available for this element");}
		}
	}
	
	private void processCategory(Category category)
	{
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	private void processPage(Page page)
	{
		String txt = org.openfuxml.addon.wiki.processor.util.WikiContentIO.loadTxt(wikiPlainDir, page.getWikiPlain());
		String result = process(txt, "article ... req?");
		org.openfuxml.addon.wiki.processor.util.WikiContentIO.writeTxt(wikiMarkupDir, page.getWikiPlain(), result);
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