package org.openfuxml.addon.wiki.processor.xhtml;

import java.util.List;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiInOutProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlSourceLoader;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiInOutProcessor;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class XhtmlReplaceProcessor extends AbstractWikiInOutProcessor implements WikiInOutProcessor
{
	static Log logger = LogFactory.getLog(XhtmlReplaceProcessor.class);
	
	private Replacements replacements;
	
	private String xHtmlText;
	
	public XhtmlReplaceProcessor(Replacements replacements) throws OfxConfigurationException
	{
		this.replacements = WikiConfigXmlSourceLoader.initReplacements(replacements);
		JaxbUtil.debug(this.replacements);
	}
	
	public void process(List<Content> lContent)
	{
		for(Content content : lContent)
		{
			String fNameXhtml = WikiContentIO.getFileFromSource(content.getSource(), "xhtml");
			String txtMarkup = WikiContentIO.loadTxt(srcDir, fNameXhtml);
			String result = process(txtMarkup);
			WikiContentIO.writeTxt(dstDir, fNameXhtml, result);
		}
	}
	
	public String process(String text)
	{
		xHtmlText=addWellFormed(text);
		xHtmlText = xHtmlText.replaceAll("&nbsp;", " ");
		for(Wikireplace replace : replacements.getWikireplace()){xhtmlReplace(replace);}
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