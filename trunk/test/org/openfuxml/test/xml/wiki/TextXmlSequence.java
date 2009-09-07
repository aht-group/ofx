package org.openfuxml.test.xml.wiki;

import java.io.File;
import java.io.IOException;

import net.sf.exlp.io.ConfigLoader;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Text;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.openfuxml.addon.wiki.WikiTemplates;
import org.openfuxml.addon.wiki.processing.XhtmlProcessor;

import de.kisner.util.LoggerInit;

public class TextXmlSequence
{
	static Logger logger = Logger.getLogger(TextXmlSequence.class);
	public static enum Status {txtFetched,txtProcessed,xhtmlRendered,xhtmlProcessed,xhtmlFinal,ofx};

	private Configuration config;
	private String dirName;
	private Document doc;
	
	public TextXmlSequence(Configuration config,String dirName)
	{
		this.config=config;
		this.dirName=dirName;
	}
	
	public void load(String article)
	{
		try
		{
			File f = new File(dirName+"/"+article+"-"+TestWiki.Status.xhtmlProcessed+".xhtml");
			doc = new SAXBuilder().build( f );
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat());
			xmlOut.output(doc, System.out);
		}
		catch (JDOMException e) {logger.error(e);}
		catch (IOException e) {logger.error(e);}
	}
	
	public void remove()
	{
		try
		{
			XhtmlProcessor xhtmlP = new XhtmlProcessor(config);
			Element result = xhtmlP.removeOfxElement(doc.getRootElement(), "ofxgallery", 0);
			XMLOutputter xmlOut = new XMLOutputter(Format.getRawFormat());
			xmlOut.output(result, System.out);
		}
		catch (IOException e) {logger.error(e);}
	}
	
	public void dissect()
	{
		Element rootE = doc.getRootElement();
		logger.debug("RootName="+rootE.getName());
		
		for(Object o :rootE.getChildren())
		{
			Element e=(Element)o;
			logger.debug(e.getName());
			for(Object oContent : e.getContent())
			{
		//		logger.debug(oContent.getClass().getName());
				if(org.jdom.Text.class.isInstance(oContent))
				{
					Text txt = (Text)oContent;
					logger.debug(oContent.getClass().getName()+": "+txt.getText());
				}
				else if(org.jdom.Element.class.isInstance(oContent))
				{
					Element child = (Element)oContent;
					logger.debug(oContent.getClass().getName()+": "+child.getName());
				}
				else
				{
					logger.warn("Unknown content: "+o.getClass().getName());
				}
			}
			logger.debug("----Child");
			for(Object oChild : e.getChildren())
			{
				logger.debug(oChild.getClass().getSimpleName());
			}
		}
	}
	
	public static void main(String[] args)
    {
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		
		ConfigLoader.add("resources/config/wiki/wiki.xml");
		Configuration config = ConfigLoader.init();
			
		WikiTemplates.init();	
			
		TextXmlSequence test = new TextXmlSequence(config,"dist");
		test.load("Bellagio");
		test.remove();
		test.dissect();
    }
}