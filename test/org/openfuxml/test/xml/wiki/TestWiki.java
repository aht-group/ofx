package org.openfuxml.test.xml.wiki;

import info.bliki.wiki.model.WikiModel;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.openfuxml.addon.wiki.OpenFuxmlGenerator;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.WikiTextFetcher;
import org.openfuxml.addon.wiki.model.WikiDefaultModel;
import org.openfuxml.addon.wiki.processing.WikiProcessor;
import org.openfuxml.addon.wiki.processing.XhtmlProcessor;
import org.openfuxml.addon.wiki.util.WikiContentIO;
import org.openfuxml.test.xml.wiki.docbook.DocbookGenerator;
import org.xml.sax.SAXException;

import de.kisner.util.LoggerInit;

public class TestWiki
{
	static Logger logger = Logger.getLogger(TestWiki.class);
	public static enum Status {txtFetched,txtProcessed,xhtmlRendered,xhtmlProcessed,xhtmlFinal,ofx};
	
	private String wikiImage,wikiTitle;
	
	private WikiProcessor wikiP;
	private XhtmlProcessor xhtmlP;
	
	private String dirName;
	private Configuration config;
	
	public TestWiki(Configuration config,String dirName)
	{
		this.config=config;
		this.dirName=dirName;
		wikiP = new WikiProcessor(config);
		xhtmlP = new XhtmlProcessor(config);
	}
	
	private String fetchTextHttp(String article)
	{
		logger.debug("Fetching article: "+article);
		WikiTextFetcher tw = new WikiTextFetcher();
		String wikiText = tw.fetchText(article);
		WikiContentIO.writeTxt("dist", article+"-"+Status.txtFetched+".txt", wikiText);
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
		File f = new File(dirName+"/"+article+"-"+Status.txtFetched+".txt");
		String wikiText;
		
		delete(new File(dirName,article+"-"+Status.txtFetched+".txt"));
		delete(new File(dirName,article+"-"+Status.txtProcessed+".txt"));
		delete(new File(dirName,article+"-"+Status.xhtmlRendered+".xhtml"));
		delete(new File(dirName,article+"-"+Status.xhtmlFinal+".xhtml"));
		delete(new File(dirName,article+"-"+Status.ofx+".xml"));
		
		if(f.exists() && f.isFile()){wikiText = WikiContentIO.loadTxt(dirName,article+"-"+Status.txtFetched+".txt");}
		else{wikiText = fetchTextHttp(article);}
		
		wikiText = wikiP.process(wikiText);
		WikiContentIO.writeTxt("dist", article+"-"+Status.txtProcessed+".txt", wikiText);
		
		wikiImage="file:///c:/temp/${image}";
		wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(wikiText);
        WikiContentIO.writeTxt("dist", article+"-"+Status.xhtmlRendered+".xhtml", xHtml);
		
		xHtml = xhtmlP.process(xHtml);
		WikiContentIO.writeXml(dirName, article+"-"+Status.xhtmlProcessed+".xhtml", xHtml);
		
		xHtml = xhtmlP.moveOfxElements();
		WikiContentIO.writeXml(dirName, article+"-"+Status.xhtmlFinal+".xhtml", xHtml);
		
		xHtml=xhtmlP.removeWellFormed(xHtml);
		
		OpenFuxmlGenerator ofxGenerator = new OpenFuxmlGenerator();
    	String htmlFooter = DocbookGenerator.FOOTER;
        
		try
		{
			String output = ofxGenerator.create(xHtml, htmlFooter, article);
			WikiContentIO.writeXml(dirName, article+"-"+Status.ofx+".xml", output);
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
			
		WikiTemplates.init();	
			
		TestWiki tw = new TestWiki(config,"dist");
		tw.testOfx();
    }
}