package org.openfuxml.wiki.processing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.openfuxml.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.wiki.data.jaxb.Wikiinjection;

public class XhtmlProcessor
{
	static Logger logger = Logger.getLogger(XhtmlProcessor.class);
	
	private String xHtmlText;
	
	private List<Wikiinjection> wikiInjections;
	
	public XhtmlProcessor(Configuration config)
	{
		wikiInjections = new ArrayList<Wikiinjection>();
		
		MultiResourceLoader mrl = new MultiResourceLoader();
		int numberTranslations = config.getStringArray("wikiprocessor/file").length;
		for(int i=1;i<=numberTranslations;i++)
		{
			loadInjections(config.getString("wikiprocessor/file["+i+"]"),mrl);
		}
		logger.debug("Injections loaded: "+wikiInjections.size());
	}
	
	private void loadInjections(String xmlFile,MultiResourceLoader mrl)
	{
		Wikicontainer container=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Wikicontainer.class);
			Unmarshaller u = jc.createUnmarshaller();
			container = (Wikicontainer)u.unmarshal(mrl.searchIs(xmlFile));
			wikiInjections.addAll(container.getWikiinjection());
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public String process(String text)
	{
		xHtmlText=addWellFormed(text);
		xHtmlText = xHtmlText.replace("&#160;", " ");
		xHtmlText = xHtmlText.replaceAll("&nbsp;", " ");
		for(Wikiinjection inject : wikiInjections){repairXml(inject);}
		tryJdom();
		return this.xHtmlText;
	}
	
	public String addWellFormed(String text)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>");
		sb.append("<wiki>");
		sb.append(text);
		sb.append("</wiki>");
		return sb.toString();
	}
	
	public String removeWellFormed(String text)
	{
		int from = text.indexOf("<wiki>")+6;
		int to = text.lastIndexOf("</wiki>");
		return text.substring(from,to);
	}
	
	private void tryJdom()
	{
		Document doc = null;
		try
		{
//			System.out.print(xHtmlText);
			Reader sr = new StringReader(xHtmlText);  
			doc = new SAXBuilder().build(sr);
		}
		catch (JDOMException e) {e.printStackTrace();}
		catch (IOException e) {logger.error(e);}
	}
	
	private void repairXml(Wikiinjection inject)
	{
		while(xHtmlText.indexOf("&#60;"+inject.getId()+"&#62;")>0)
		{
			StringBuffer sbDebug = new StringBuffer();
			int from = xHtmlText.indexOf("&#60;"+inject.getId()+"&#62;");
			int to = xHtmlText.indexOf("&#60;/"+inject.getId()+"&#62;");
			StringBuffer sb = new StringBuffer();
				sb.append(xHtmlText.substring(0, from-1));
				sb.append("<"+inject.getId()+">");
				sb.append(xHtmlText.substring(from+inject.getId().length()+10, to));
				sb.append("</"+inject.getId()+">");
				sb.append(xHtmlText.substring(to+inject.getId().length()+11, xHtmlText.length()));
			xHtmlText=sb.toString();
		}
	}
}
