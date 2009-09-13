package org.openfuxml.addon.wiki;

import info.bliki.wiki.model.WikiModel;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processing.InjectionProcessor;
import org.openfuxml.addon.wiki.processing.WikiProcessor;
import org.openfuxml.addon.wiki.processing.XhtmlProcessor;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.addon.wiki.util.WikiTextFetcher;
import org.openfuxml.test.xml.wiki.docbook.DocbookGenerator;
import org.xml.sax.SAXException;

import de.kisner.util.LoggerInit;

public class OfxWikiEngine
{
	static Logger logger = Logger.getLogger(OfxWikiEngine.class);
	public static enum Status {txtFetched,txtProcessed,xhtmlRendered,xhtmlProcessed,xhtmlFinal,ofx};
	
	private String wikiImage,wikiTitle;
	
	private WikiProcessor wikiP;
	private XhtmlProcessor xhtmlP;
	
	private String dirWiki,dirOfx;
	private Configuration config;
	
	public OfxWikiEngine(Configuration config)
	{
		this.config=config;
		dirWiki=config.getString("/ofx/dir[@type='wiki']");
		dirOfx=config.getString("/ofx/dir[@type='ofx']");
		wikiP = new WikiProcessor(config);
		xhtmlP = new XhtmlProcessor(config);
	}
	
	private String fetchTextHttp(String article)
	{
		logger.debug("Fetching article: "+article);
		WikiTextFetcher tw = new WikiTextFetcher();
		String wikiText = tw.fetchText(article);
		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtFetched+".txt", wikiText);
		return wikiText;
	}
	
	private void delete(File f)
	{
		if(f.exists() && f.isFile())
		{
			logger.debug("Delete: "+f.getAbsolutePath());
			f.delete();
		}
	}
	
	public void testOfx()
	{
		String article = config.getString("wiki/article");
		File f = new File(dirWiki+"/"+article+"-"+Status.txtFetched+".txt");
		String wikiText;
		
		delete(new File(dirWiki,article+"-"+Status.txtFetched+".txt"));
		delete(new File(dirWiki,article+"-"+Status.txtProcessed+".txt"));
		delete(new File(dirWiki,article+"-"+Status.xhtmlRendered+".xhtml"));
		delete(new File(dirWiki,article+"-"+Status.xhtmlFinal+".xhtml"));
		delete(new File(dirWiki,article+"-"+Status.ofx+".xml"));
		
		if(f.exists() && f.isFile()){wikiText = WikiContentIO.loadTxt(dirWiki,article+"-"+Status.txtFetched+".txt");}
		else{wikiText = fetchTextHttp(article);}
		
		wikiText = wikiP.process(wikiText);
		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtProcessed+".txt", wikiText);
		
		wikiImage="file:///c:/temp/${image}";
		wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(wikiText);
        WikiContentIO.writeTxt(dirWiki, article+"-"+Status.xhtmlRendered+".xhtml", xHtml);
		
		xHtml = xhtmlP.process(xHtml);
		WikiContentIO.writeXml(dirWiki, article+"-"+Status.xhtmlProcessed+".xhtml", xHtml);
		
		xHtml = xhtmlP.moveOfxElements();
		WikiContentIO.writeXml(dirWiki, article+"-"+Status.xhtmlFinal+".xhtml", xHtml);
		
		xHtml=xhtmlP.removeWellFormed(xHtml);
		
		OpenFuxmlGenerator ofxGenerator = new OpenFuxmlGenerator(config);
    	String htmlFooter = DocbookGenerator.FOOTER;
        
		InjectionProcessor ip = new InjectionProcessor(config);
		ip.processInjections();
    	
		try
		{
			String output = ofxGenerator.create(xHtml, htmlFooter, article);
			WikiContentIO.writeXml(dirOfx, article+"-"+Status.ofx+".xml", output);
		}
		catch (IOException e) {logger.error(e);}
		catch (ParserConfigurationException e) {logger.error(e);}
		catch (XMLStreamException e) {logger.error(e);}
		catch (SAXException e) {logger.error(e);}
    }
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
		
		WikiConfigChecker.check(config);
		
		WikiTemplates.init();	
			
		OfxWikiEngine tw = new OfxWikiEngine(config);
		tw.testOfx();
    }
}