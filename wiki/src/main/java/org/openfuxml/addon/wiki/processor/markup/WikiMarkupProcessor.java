package org.openfuxml.addon.wiki.processor.markup;

import java.io.File;

import org.exlp.util.jx.JaxbUtil;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlXpathHelper;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.addon.wiki.Category;
import org.openfuxml.model.xml.addon.wiki.Content;
import org.openfuxml.model.xml.addon.wiki.Injections;
import org.openfuxml.model.xml.addon.wiki.Markup;
import org.openfuxml.model.xml.addon.wiki.ObjectFactory;
import org.openfuxml.model.xml.addon.wiki.Page;
import org.openfuxml.model.xml.addon.wiki.Replacements;
import org.openfuxml.model.xml.addon.wiki.Template;
import org.openfuxml.model.xml.addon.wiki.Templates;
import org.openfuxml.model.xml.addon.wiki.Wikiinjection;
import org.openfuxml.model.xml.addon.wiki.Wikireplace;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.io.StringIO;

public class WikiMarkupProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiMarkupProcessor.class);
	
	public static enum InjectionType {xml,wiki};
	
	private Replacements replacements;
	private Injections injections;
	private Templates templates;
	
	private String wikiText;
	private ObjectFactory of;
	
	private int templateCounter;
	private static String templateStartDelemiter = "{{";
	private static String templateEndDelemiter = "}}";
	
	@Deprecated
	private File dirInjection;
	private int injectionId;
	
	public WikiMarkupProcessor(Cmp cmp) throws OfxConfigurationException
	{
		this(cmp.getPreprocessor().getWiki().getMarkupProcessor().getReplacements(),
				cmp.getPreprocessor().getWiki().getMarkupProcessor().getInjections(),
				cmp.getPreprocessor().getWiki().getTemplates());
	}
	
	public WikiMarkupProcessor(Replacements replacements, Injections injections, Templates templates) throws OfxConfigurationException
	{
		this.templates=templates;
		this.replacements = WikiConfigXmlXpathHelper.initReplacements(replacements);
		this.injections = WikiConfigXmlXpathHelper.initInjections(injections);
		templateCounter=0;
	}
	
	@Override
	protected void processCategory(Content content) throws OfxInternalProcessingException
	{
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	@Override
	protected void processPage(Content content) throws OfxInternalProcessingException
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	protected void processPage(Page page) throws OfxInternalProcessingException
	{
		logger.debug("Processing Page: "+page.getName());
		String fName = page.getFile()+"."+WikiProcessor.WikiFileExtension.txt;
		String txt = StringIO.loadTxt(srcDir, fName);
		String result = process(txt, page.getName());
		StringIO.writeTxt(dstDir, fName, result);
	}
	
	
	public String process(String wikiPlain, String article) throws OfxInternalProcessingException
	{
		//TODO Check the meaning of article!!
		this.wikiText=wikiPlain;
		for(Wikireplace replace : replacements.getWikireplace()){processReplacements(replace);}
		for(Wikiinjection inject : injections.getWikiinjection()){wikiInject(inject,article);}
		for(Template template : templates.getTemplate()){processTemplate(template);}
		return this.wikiText;
	}

	private void processReplacements(Wikireplace replace)
	{
		wikiText = wikiText.replaceAll(replace.getFrom(), replace.getTo());
	}
	
	private void processTemplate(Template template) throws OfxInternalProcessingException
	{
		logger.debug("Processing Template: "+template.getName());
		int beginIndex=-1;
		while((beginIndex=wikiText.indexOf(templateStartDelemiter+template.getName()))>=0)
		{
			//TODO Nesting templates
			logger.warn("Nesting Templates are not supported!!");
			int endIndex = wikiText.indexOf(templateEndDelemiter);
			StringBuffer sb = new StringBuffer();
			sb.append(wikiText.substring(0, beginIndex));
			sb.append(createExternalTemplate(template,wikiText.substring(beginIndex+templateStartDelemiter.length()+template.getName().length(), endIndex)));
			sb.append(wikiText.substring(endIndex+templateEndDelemiter.length(), wikiText.length()));
			wikiText = sb.toString();
		}
	}
	
	private String createExternalTemplate(Template templateDef, String templateMarkup) throws OfxInternalProcessingException
	{	
		Template template = new Template();
		template.setName(templateDef.getName());
		template.setId(getNextTemplateId());
		template.setMarkup(new Markup());
		template.getMarkup().setValue(templateMarkup);
		
		File f = new File(this.getDir(WikiProcessor.WikiDir.wikiTemplate), template.getId()+"."+WikiProcessor.WikiFileExtension.xml);
		JaxbUtil.save(f, template, true);
		
		StringBuffer sb = new StringBuffer();
		sb.append(System.lineSeparator());
		sb.append("<wiki:injection id=\"");
		sb.append(template.getId());
		sb.append("\"/>");
		sb.append(System.lineSeparator());
		
		return sb.toString();
	}
	
	private void wikiInject(Wikiinjection inject, String article)
	{
		while(wikiText.indexOf("<"+inject.getWikitag()+">")>0)
		{
			logger.warn("Injection should start, but currently NYI");
			inject.setId(""+injectionId);injectionId++;
			inject.setArticle(article);
			
			StringBuffer sbDebug = new StringBuffer();
			String startTag = "<"+inject.getWikitag()+">";
			int from = wikiText.indexOf(startTag);
			int to = wikiText.indexOf("</"+inject.getWikitag()+">");
			
			StringBuffer injectionSb = WikiContentIO.toString(inject);
			logger.debug(injectionSb.toString());
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
					sb.append(System.lineSeparator());
				}
				sb.append(wikiText.substring(to+inject.getWikitag().length()+3+1,wikiText.length()));
			wikiText=sb.toString();
			sbDebug.append(" newSize="+wikiText.length());
			logger.debug(sbDebug.toString());
		}
	}
	
	private String getNextTemplateId()
	{
		templateCounter++;
		return ""+templateCounter;
	}
}