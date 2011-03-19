package org.openfuxml.addon.wiki.processing;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Ofx;
import org.openfuxml.addon.wiki.data.jaxb.Wikiinjection;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processing.xhtml.OfxPushUp;
import org.openfuxml.addon.wiki.processing.xhtml.XhtmlAHxMerge;

public class XhtmlProcessor
{
	static Log logger = LogFactory.getLog(XhtmlProcessor.class);
	
	private String xHtmlText;
	
	private List<Wikiinjection> wikiInjections;
	private List<Wikireplace> xhtmlReplaces;
	
	public XhtmlProcessor(Configuration config)
	{
		wikiInjections = new ArrayList<Wikiinjection>();
		xhtmlReplaces = new ArrayList<Wikireplace>();
		
		int numberTranslations = config.getStringArray("xhtmlprocessor/file").length;
		for(int i=1;i<=numberTranslations;i++)
		{
			String xmlFile = config.getString("xhtmlprocessor/file["+i+"]");
			try
			{
				Ofx container;
				container = (Ofx)JaxbUtil.loadJAXB(xmlFile, Ofx.class);
				wikiInjections.addAll(container.getWikiinjection());
				xhtmlReplaces.addAll(container.getWikireplace());
			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		logger.debug("Injections loaded: "+wikiInjections.size());
	}
	
	public String processFinal(String xHtml)
	{
		OfxPushUp pushUp = new OfxPushUp();
		XhtmlAHxMerge merger = new XhtmlAHxMerge();
		
		xHtml = pushUp.moveOfxElements(xHtml);
		xHtml = merger.merge(xHtml);
		return xHtml;
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
}