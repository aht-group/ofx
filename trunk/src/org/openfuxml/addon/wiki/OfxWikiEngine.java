package org.openfuxml.addon.wiki;

import info.bliki.wiki.model.WikiModel;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import net.sf.exlp.io.ConfigLoader;
import net.sf.exlp.util.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.output.Format;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processing.InjectionProcessor;
import org.openfuxml.addon.wiki.processing.WikiProcessor;
import org.openfuxml.addon.wiki.processing.XhtmlProcessor;
import org.openfuxml.addon.wiki.processing.XmlProcessor;
import org.openfuxml.addon.wiki.util.WikiConfigChecker;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.addon.wiki.util.WikiTextFetcher;
import org.xml.sax.SAXException;

import de.kisner.util.LoggerInit;

public class OfxWikiEngine
{
	static Logger logger = Logger.getLogger(OfxWikiEngine.class);
	public static enum Status {txtFetched,txtProcessed,xhtmlRendered,xhtmlProcessed,xhtmlFinal,ofx};
	
	private String wikiImage,wikiTitle;
	
	private WikiProcessor wikiP;
	private XhtmlProcessor xhtmlP;
	private XmlProcessor xmlP;
	
	private String dirWiki,dirOfx;
	private Configuration config;
	private String article;
	
	public OfxWikiEngine(Configuration config, String article)
	{
		this.article=article;
		this.config=config;
		dirWiki=config.getString("/ofx/dir[@type='wiki']");
		dirOfx=config.getString("/ofx/dir[@type='ofx']");
		wikiP = new WikiProcessor(config,article);
		xhtmlP = new XhtmlProcessor(config);
		xmlP = new XmlProcessor(config);
	}
	
	private String fetchTextHttp()
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
	
	public void transform()
	{
		File f = new File(dirWiki+"/"+article+"-"+Status.txtFetched+".txt");
		String wikiText;
		
		delete(new File(dirWiki,article+"-"+Status.txtFetched+".txt"));
		delete(new File(dirWiki,article+"-"+Status.txtProcessed+".txt"));
		delete(new File(dirWiki,article+"-"+Status.xhtmlRendered+".xhtml"));
		delete(new File(dirWiki,article+"-"+Status.xhtmlFinal+".xhtml"));
		delete(new File(dirWiki,article+"-"+Status.ofx+".xml"));
		
		if(f.exists() && f.isFile()){wikiText = WikiContentIO.loadTxt(dirWiki,article+"-"+Status.txtFetched+".txt");}
		else{wikiText = fetchTextHttp();}
		
		wikiText = wikiP.process(wikiText);
		WikiContentIO.writeTxt(dirWiki, article+"-"+Status.txtProcessed+".txt", wikiText);
		
		wikiImage="file:///c:/temp/${image}";
		wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(wikiText);
        WikiContentIO.writeTxt(dirWiki, article+"-"+Status.xhtmlRendered+".xhtml", xHtml);
		
		xHtml = xhtmlP.process(xHtml);
		WikiContentIO.writeXml(dirWiki, article+"-"+Status.xhtmlProcessed+".xhtml", xHtml);
		
		xHtml = xhtmlP.processFinal(xHtml);
		WikiContentIO.writeXml(dirWiki, article+"-"+Status.xhtmlFinal+".xhtml", xHtml);
		
		xHtml=xhtmlP.removeWellFormed(xHtml);
		
		OpenFuxmlGenerator ofxGenerator = new OpenFuxmlGenerator(config);
    	String htmlFooter = "    </body>\n</html>";
        
		InjectionProcessor ip = new InjectionProcessor(config);
		ip.processInjections();
    	
		try
		{
			String output = ofxGenerator.create(xHtml, htmlFooter, article);
			File saveXml = new File(dirOfx,article+"-"+Status.ofx+".xml");
			Document doc = xmlP.process(output);
			JDomUtil.save(doc, saveXml, Format.getRawFormat());
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
		String article = config.getString("wiki/article");
		
		OfxWikiEngine tw = new OfxWikiEngine(config,article);
		tw.transform();
    }
}