package org.openfuxml.addon.wiki.processor.xhtml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Category;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Page;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.mods.OfxPushUp;
import org.openfuxml.addon.wiki.processor.xhtml.mods.XhtmlAHxMerge;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class XhtmlFinalProcessor extends AbstractWikiProcessor implements WikiProcessor
{
	static Log logger = LogFactory.getLog(XhtmlFinalProcessor.class);
	
	public XhtmlFinalProcessor() throws OfxConfigurationException
	{
		
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
		String fNameXhtml = page.getFile()+"."+WikiProcessor.WikiFileExtension.xhtml;
		String txtMarkup = WikiContentIO.loadTxt(srcDir, fNameXhtml);
		String result = process(txtMarkup);
		WikiContentIO.writeTxt(dstDir, fNameXhtml, result);
	}
	
	public String process(String xHtml)
	{
		OfxPushUp pushUp = new OfxPushUp();
		XhtmlAHxMerge merger = new XhtmlAHxMerge();
		
		xHtml = pushUp.moveOfxElements(xHtml);
		xHtml = merger.merge(xHtml);
		xHtml = removeWellFormed(xHtml);
		return xHtml;
	}
	
	public String removeWellFormed(String text)
	{
		int from = text.indexOf("<wiki>")+6;
		int to = text.lastIndexOf("</wiki>");
		return text.substring(from,to);
	}
}