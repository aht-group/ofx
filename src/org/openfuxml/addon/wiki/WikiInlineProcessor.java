package org.openfuxml.addon.wiki;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.data.jaxb.Cmp;

public class WikiInlineProcessor
{
	static Log logger = LogFactory.getLog(WikiInlineProcessor.class);
	
	public static boolean debugOutput = false;
	
	private WikiMarkupProcessor wpMarkup;
	private WikiModelProcessor wpModel;
	private XhtmlReplaceProcessor wpXhtmlR;
	private XhtmlFinalProcessor wpXhtmlF;
	private WikiPageProcessor ofxP;
	
	public WikiInlineProcessor(Cmp cmp) throws OfxConfigurationException
	{
		MarkupProcessor mpXml = cmp.getPreprocessor().getWiki().getMarkupProcessor();
		XhtmlProcessor  xpXml = cmp.getPreprocessor().getWiki().getXhtmlProcessor();
		
		wpMarkup = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections());
		wpModel = new WikiModelProcessor();
		wpXhtmlR = new XhtmlReplaceProcessor(xpXml.getReplacements());
		wpXhtmlF = new XhtmlFinalProcessor();
		ofxP = new WikiPageProcessor();
	}
	
	public Section toOfx(String wikiPlain) throws OfxInternalProcessingException
	{
		if(debugOutput){logger.debug("wikiPlain: "+wikiPlain);}
		String wikiMarkup = wpMarkup.process(wikiPlain, "ARTICLE ... ");
		if(debugOutput){logger.debug("wikiMarkup: "+wikiMarkup);}
		String xhtmlModel = wpModel.process(wikiMarkup);
		if(debugOutput){logger.debug("xhtmlModel: "+xhtmlModel);}
		String xhtmlReplace = wpXhtmlR.process(xhtmlModel);
		if(debugOutput){logger.debug("xhtmlReplace: "+xhtmlReplace);}
		String xhtmlFinal = wpXhtmlF.process(xhtmlReplace);
		if(debugOutput){logger.debug("xhtmlFinal: "+xhtmlFinal);}
		Element xml = ofxP.process(xhtmlFinal);
		JDomUtil.debug(xml);
		Section section = (Section)JDomUtil.toJaxb(xml, Section.class);
		return section;		
	}
}