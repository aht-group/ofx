package org.openfuxml.addon.wiki;

import java.io.File;
import java.io.FileNotFoundException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;
import org.openfuxml.addon.wiki.data.jaxb.MarkupProcessor;
import org.openfuxml.addon.wiki.data.jaxb.XhtmlProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiMarkupProcessor;
import org.openfuxml.addon.wiki.processor.markup.WikiModelProcessor;
import org.openfuxml.addon.wiki.processor.ofx.xml.WikiPageProcessor;
import org.openfuxml.addon.wiki.processor.util.WikiContentIO;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlFinalProcessor;
import org.openfuxml.addon.wiki.processor.xhtml.XhtmlReplaceProcessor;
import org.openfuxml.renderer.data.exception.OfxConfigurationException;
import org.openfuxml.renderer.data.exception.OfxInternalProcessingException;
import org.openfuxml.renderer.data.jaxb.Cmp;

public class WikiInlineProcessor
{
	static Log logger = LogFactory.getLog(WikiInlineProcessor.class);
	
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
	
	public void toOfx(String wikiPlain) throws OfxInternalProcessingException
	{
		logger.debug("wikiPlain: "+wikiPlain);
		String wikiMarkup = wpMarkup.process(wikiPlain, "ARTICLE ... ");
		logger.debug("wikiMarkup: "+wikiMarkup);
		String xhtmlModel = wpModel.process(wikiMarkup);
		logger.debug("xhtmlModel: "+xhtmlModel);
		String xhtmlReplace = wpXhtmlR.process(xhtmlModel);
		logger.debug("xhtmlReplace: "+xhtmlReplace);
		String xhtmlFinal = wpXhtmlF.process(xhtmlReplace);
		logger.debug("xhtmlFinal: "+xhtmlFinal);
		Element xml = ofxP.process(xhtmlFinal);
		JDomUtil.debug(xml);		
	}
	
	public static void main(String[] args) throws FileNotFoundException, OfxConfigurationException, OfxInternalProcessingException
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		String propFile = "resources/properties/user.properties";
		if(args.length==1){propFile=args[0];}
		ConfigLoader.add(propFile);
		Configuration config = ConfigLoader.init();
			
		File f = new File("resources/data/wiki/2.txt");
		String wikiTxt = WikiContentIO.loadTxt(f);
		
		String fNameCmp = config.getString("ofx.xml.cmp");
		Cmp cmp = (Cmp)JaxbUtil.loadJAXB(fNameCmp, Cmp.class);
		
		WikiInlineProcessor wikiInline = new WikiInlineProcessor(cmp);
		wikiInline.toOfx(wikiTxt);
    }
}