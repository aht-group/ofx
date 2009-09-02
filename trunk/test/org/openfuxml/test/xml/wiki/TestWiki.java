package org.openfuxml.test.xml.wiki;

import info.bliki.wiki.model.WikiModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.openfuxml.test.xml.wiki.docbook.DocbookGenerator;
import org.openfuxml.wiki.OpenFuxmlGenerator;
import org.openfuxml.wiki.WikiTemplates;
import org.openfuxml.wiki.WikiTextFetcher;
import org.openfuxml.wiki.model.WikiDefaultModel;
import org.openfuxml.wiki.processing.WikiProcessor;
import org.openfuxml.wiki.processing.XhtmlProcessor;
import org.xml.sax.SAXException;

import de.kisner.util.LoggerInit;

public class TestWiki
{
	static Logger logger = Logger.getLogger(TestWiki.class);
	
	private String wikiText;
	private String wikiImage,wikiTitle;
	
	private WikiProcessor wikiP;
	private XhtmlProcessor xhtmlP;
	
	public TestWiki()
	{
		wikiP = new WikiProcessor();
		xhtmlP = new XhtmlProcessor();
	}
	
	private String fetchTextHttp()
	{
		WikiTextFetcher tw = new WikiTextFetcher();
		wikiText = tw.fetchText("Bellagio");
		
		wikiText = wikiP.process(wikiText);
		
		logger.debug("Modified: "+wikiText);
		
		wikiImage="file:///c:/temp/${image}";
		wikiTitle="file:///c:/temp/${title}";
		
        WikiModel myWikiModel = new WikiDefaultModel(wikiImage,wikiTitle);
        String xHtml = myWikiModel.render(wikiText);
        
        try
		{
			logger.debug("XHTML: "+xHtml);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("dist/xhtml.txt")));
			bw.write(xHtml);
			bw.close();
		}
		catch (IOException e1) {logger.error(e1);}
		return xHtml;
	}
	
	private String fetchTextFile()
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			logger.debug("XHTML: ");
			BufferedReader bw = new BufferedReader(new FileReader(new File("dist/xhtml.txt")));
			while(bw.ready())
			{
				sb.append(bw.readLine());
			}
			bw.close();
		}
		catch (IOException e) {logger.error(e);}
		return sb.toString();
	}
	
	public void testOfx()
	{
		String xHtml;
		xHtml = fetchTextFile();
		xHtml = fetchTextHttp();
		
		xHtml = xhtmlP.process(xHtml);
		
		OpenFuxmlGenerator ofxGenerator = new OpenFuxmlGenerator();
		
    	String htmlFooter = DocbookGenerator.FOOTER;
    	String htmlTitle = "Big Docbook Test";
        
		try
		{
			String output = ofxGenerator.create(xHtml, htmlFooter, htmlTitle);
			logger.debug("XML: "+output);
		}
		catch (IOException e) {logger.error(e);}
		catch (ParserConfigurationException e) {logger.error(e);}
		catch (XMLStreamException e) {logger.error(e);}
		catch (SAXException e) {logger.error(e);}
    }
	
	public void testHtml()
	{
		wikiImage="http://www.mywiki.com/wiki/${image}";
		wikiTitle="http://www.mywiki.com/wiki/${title}";
		WikiModel wikiModel = new WikiModel(wikiImage,wikiTitle);
		String htmlStr = wikiModel.render(wikiText);
		System.out.print(htmlStr);
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		WikiTemplates.init();	
			
		TestWiki tw = new TestWiki();
//		tw.testHtml();
		tw.testOfx();
    }
}