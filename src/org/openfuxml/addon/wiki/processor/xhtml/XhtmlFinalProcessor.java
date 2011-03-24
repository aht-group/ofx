package org.openfuxml.addon.wiki.processor.xhtml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openfuxml.addon.wiki.data.jaxb.Content;
import org.openfuxml.addon.wiki.data.jaxb.Contents;
import org.openfuxml.addon.wiki.processor.util.AbstractWikiInOutProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.util.WikiInOutProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.mods.OfxPushUp;
import org.openfuxml.addon.wiki.processor.xhtml.mods.XhtmlAHxMerge;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;

public class XhtmlFinalProcessor extends AbstractWikiInOutProcessor implements WikiInOutProcessor
{
	static Log logger = LogFactory.getLog(XhtmlFinalProcessor.class);
	
	public XhtmlFinalProcessor() throws OfxConfigurationException
	{
		
	}
	
	public void process(Contents wikiQueries)
	{
		for(Content content : wikiQueries.getContent())
		{
			String fNameXhtml = WikiContentIO.getFileFromSource(content.getSource(), "xhtml");
			String txtMarkup = WikiContentIO.loadTxt(srcDir, fNameXhtml);
			String result = process(txtMarkup);
			WikiContentIO.writeTxt(dstDir, fNameXhtml, result);
		}
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