package org.openfuxml.wiki.processing;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.exlp.io.StringBufferOutputStream;
import net.sf.exlp.io.resourceloader.MultiResourceLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.wiki.data.jaxb.Wikireplace;

public class XhtmlProcessor
{
	static Logger logger = Logger.getLogger(XhtmlProcessor.class);
	
	private String xHtmlText;
	
	private List<Wikiinjection> wikiInjections;
	private List<Wikireplace> xhtmlReplaces;
	
	public XhtmlProcessor(Configuration config)
	{
		wikiInjections = new ArrayList<Wikiinjection>();
		xhtmlReplaces = new ArrayList<Wikireplace>();
		
		MultiResourceLoader mrl = new MultiResourceLoader();
		int numberTranslations = config.getStringArray("xhtmlprocessor/file").length;
		for(int i=1;i<=numberTranslations;i++)
		{
			loadWikiContainer(config.getString("xhtmlprocessor/file["+i+"]"),mrl);
		}
		logger.debug("Injections loaded: "+wikiInjections.size());
	}
	
	private void loadWikiContainer(String xmlFile,MultiResourceLoader mrl)
	{
		Wikicontainer container=null;
		try
		{
			JAXBContext jc = JAXBContext.newInstance(Wikicontainer.class);
			Unmarshaller u = jc.createUnmarshaller();
			container = (Wikicontainer)u.unmarshal(mrl.searchIs(xmlFile));
			wikiInjections.addAll(container.getWikiinjection());
			xhtmlReplaces.addAll(container.getWikireplace());
		}
		catch (JAXBException e) {logger.error(e);}
		catch (FileNotFoundException e) {logger.error(e);}
	}
	
	public String process(String text)
	{
		xHtmlText=addWellFormed(text);
		xHtmlText = xHtmlText.replaceAll("&nbsp;", " ");
		for(Wikireplace replace : xhtmlReplaces){xhtmlReplace(replace);}
		for(Wikiinjection inject : wikiInjections){repairXml(inject);}
		return this.xHtmlText;
	}
	
	private void xhtmlReplace(Wikireplace replace)
	{
		xHtmlText = xHtmlText.replaceAll(replace.getFrom(), replace.getTo());
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
	
	public String removeOfxElements()
	{
		Map<String,Boolean> ofxTags = new Hashtable<String,Boolean>();
		for(Wikiinjection wikiinjection : wikiInjections)
		{
			String tag = wikiinjection.getId();
			if(!ofxTags.containsKey(tag)){ofxTags.put(tag, false);}
		}
		try
		{
			Reader sr = new StringReader(xHtmlText);  
			Document doc = new SAXBuilder().build(sr);
			Element rootElement = doc.getRootElement();
			rootElement.detach();
			for(String s :ofxTags.keySet())
			{
				rootElement=removeOfxElement(rootElement,s,0);
			}
			doc.addContent(rootElement);
			
			StringBufferOutputStream sbos = new StringBufferOutputStream();
			XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());
			xmlOut.output(doc, sbos);
			xHtmlText=sbos.getStringBuffer().toString();
		}
		catch (JDOMException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
		return xHtmlText;
	}
	
	private Element removeOfxElement(Element oldRoot, String tag, int level)
	{
		Element newRoot = new Element(oldRoot.getName());
		
		//newRoot.setAttributes(oldRoot.getAttributes());
		newRoot.setText(oldRoot.getText());
//		logger.debug(oldRoot.getValue());
		StringBuffer sb = new StringBuffer();
		sb.append("Tag="+tag+" Level="+level);
		for(Object o : oldRoot.getChildren())
		{
			Element oldChild = (Element)o;
			sb.append(" "+oldChild.getName());
			if(oldChild.getName().equals(tag))
			{
				logger.debug("Detaching "+oldChild.getName());
			}
			else
			{
				Element newChild=removeOfxElement(oldChild, tag, level+1);
				newRoot.addContent(newChild);
			}
		}
		logger.debug(sb);
		return newRoot;
	}
}
