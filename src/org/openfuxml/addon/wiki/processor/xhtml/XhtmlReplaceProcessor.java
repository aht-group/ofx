package org.openfuxml.addon.wiki.processor.xhtml;

import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.data.jaxb.Replacements;
import org.openfuxml.addon.wiki.data.jaxb.Wikireplace;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiConfigXmlSourceLoader;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class XhtmlReplaceProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(XhtmlReplaceProcessor.class);
	
	private Replacements replacements;
	
	private String xHtmlText;
	
	public XhtmlReplaceProcessor(Replacements replacements) throws OfxConfigurationException
	{
		this.replacements = WikiConfigXmlSourceLoader.initReplacements(replacements);
		JaxbUtil.debug(this.replacements);
	}
	
	@Override
	protected void processCategory(Content content)
	{
		Category category = content.getCategory();
		for(Page page : category.getPage())
		{
			processPage(page);
		}
	}
	
	@Override
	protected void processPage(Content content)
	{
		Page page = content.getPage();
		processPage(page);
	}
	
	public void processPage(Page page)
	{
		String fNameModel = page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
		String txtMarkup = WikiContentIO.loadTxt(srcDir, fNameModel);
		String result = process(txtMarkup);
		WikiContentIO.writeTxt(dstDir, fNameModel, result);
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