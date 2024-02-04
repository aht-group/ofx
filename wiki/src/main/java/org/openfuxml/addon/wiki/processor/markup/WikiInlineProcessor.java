package org.openfuxml.addon.wiki.processor.markup;

import net.sf.exlp.util.xml.JDomUtil;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Element;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.model.xml.addon.wiki.MarkupProcessor;
import org.openfuxml.model.xml.addon.wiki.Templates;
import org.openfuxml.model.xml.addon.wiki.XhtmlProcessor;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.xml.renderer.cmp.Cmp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WikiInlineProcessor
{
	final static Logger logger = LoggerFactory.getLogger(WikiInlineProcessor.class);
	
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
		Templates   templates = cmp.getPreprocessor().getWiki().getTemplates();
		
		wpMarkup = new WikiMarkupProcessor(mpXml.getReplacements(), mpXml.getInjections(),templates);
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
		if(debugOutput){logger.debug(JaxbUtil.toString(xml));}
//		;
		Section section = (Section)JDomUtil.toJaxb(xml, Section.class);
		return section;		
	}
}