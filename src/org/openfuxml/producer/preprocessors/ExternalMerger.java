package org.openfuxml.producer.preprocessors;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

public class ExternalMerger
{
	private static Logger logger = Logger.getLogger(ExternalMerger.class);
	
	private File rootFile;
	private Document doc;
	
	private Namespace ns;
	private XPath xpath;
	
	public ExternalMerger(File rootFile)
	{
		this.rootFile=rootFile;
		doc = JDomUtil.load(rootFile);
		try
		{
			ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
			xpath = XPath.newInstance("*[@external='true']");
			xpath.addNamespace(ns);
		}
		catch (JDOMException e) {logger.error(e);}
		xpath.addNamespace(ns);
	}
	
	public Document merge()
	{
		Element rootElement = doc.getRootElement();

		rootElement.detach();
		
		doc.setRootElement(mergeRecursive(rootElement));
		return doc;
	}
	
	public Element getExternal()
	{
		Element rootElement = doc.getRootElement();
		rootElement.detach();
		
		return mergeRecursive(rootElement);
	}
	
	private Element mergeRecursive(Element rootElement)
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement);
			logger.debug(list.size()+" external sources in "+rootFile.getAbsolutePath());
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next();
				String source =childElement.getAttribute("source").getValue();
				File childFile = new File(rootFile.getParentFile(),source);
				ExternalMerger em = new ExternalMerger(childFile);
				Element eEx = em.getExternal();
				int index = childElement.getParentElement().indexOf(childElement);
				childElement.getParentElement().setContent(index, eEx);
				childElement.detach();
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return rootElement;
	}
			
	public static void main (String[] args) throws Exception
	{
		LoggerInit loggerInit = new LoggerInit("log4j.xml");	
			loggerInit.addAltPath("resources/config");
			loggerInit.init();
		logger.debug("Testing ExternalMerger");
		
		File f = new File("resources/data/xml/exmerge/chapter-1.xml");
		ExternalMerger exMerger = new ExternalMerger(f);
		Document doc = exMerger.merge();
		JDomUtil.debug(doc);
	}
}