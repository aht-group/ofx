package org.openfuxml.addon.wiki.processing;

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
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.addon.wiki.data.jaxb.Wikicontainer;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;

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
		repairXml();
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
	
	private void repairXml()
	{
		String startTag="&#60;wikiinjection";
		String endTag="/&#62;";
		while(xHtmlText.indexOf(startTag)>0)
		{
			int from = xHtmlText.indexOf(startTag);
			int to = xHtmlText.indexOf(endTag);
			
			String insideTag = xHtmlText.substring(from+startTag.length(), to);
			insideTag=insideTag.replaceAll("&#34;", "\"");
			
			StringBuffer sb = new StringBuffer();
				sb.append(xHtmlText.substring(0, from-1));
				sb.append("<wikiinjection");
				sb.append(insideTag);
				sb.append("/>");
				sb.append(xHtmlText.substring(to+endTag.length(), xHtmlText.length()));
			xHtmlText=sb.toString();
		}
	}
	
	public String removeOfxElements()
	{
		Map<String,Boolean> ofxTags = new Hashtable<String,Boolean>();
		for(Wikiinjection wikiinjection : wikiInjections)
		{
			String tag = wikiinjection.getOfxtag();
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
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat());
			xmlOut.output(doc, sbos);
			xHtmlText=sbos.getStringBuffer().toString();
		}
		catch (JDOMException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
		return xHtmlText;
	}
	
	//TODO public here ist only for testing, remove this later!
	public Element removeOfxElement(Element oldRoot, String tag, int level)
	{
		Element newRoot = new Element(oldRoot.getName());
		
		for(Object oAtt : oldRoot.getAttributes())
		{
			Attribute att = (Attribute)oAtt;
			Attribute newAtt = new Attribute(att.getName(),att.getValue());
			newRoot.setAttribute(newAtt);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Tag="+tag+" Level="+level);
		for(Object o : oldRoot.getContent())
		{
			if(org.jdom.Text.class.isInstance(o))
			{
				Text txt = (Text)o;
				Text newText = new Text(txt.getText());
				newRoot.addContent(newText);
				sb.append(" txt");
			}
			else if(org.jdom.Element.class.isInstance(o))
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
			else {logger.warn("Unknown content: "+o.getClass().getName());}
		}
		logger.trace(sb);
		return newRoot;
	}
}