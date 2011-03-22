package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.io.LoggerInit;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.model.ejb.OfxDocument;

public class OfxExternalMerger
{
	static Log logger = LogFactory.getLog(OfxExternalMerger.class);
	
	private File rootFile;
	private Document doc;
	
	private XPath xpath;
	
	public OfxExternalMerger(File rootFile)
	{
		this.rootFile=rootFile;
		doc = JDomUtil.load(rootFile);
		try
		{
			
			xpath = XPath.newInstance("//*[@external='true']");
			xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));
			
//			List<?> list = xpath.selectNodes(doc.getRootElement());
//			logger.debug(list.size()+" hits");
			
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public OfxDocument mergeToOfx()
	{
		Document mergedDoc =  mergeToDoc();
		OfxDocument ofxDoc = (OfxDocument)JDomUtil.toJaxb(mergedDoc, OfxDocument.class);
		return ofxDoc;
	}
	
	public Document mergeToDoc()
	{
		Element rootElement = doc.getRootElement();

//		rootElement.detach();
		Element result = mergeRecursive(rootElement);
		result.detach();
		doc.setRootElement(result);
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
//			logger.debug(xpath.getXPath());
			List<?> list = xpath.selectNodes(rootElement);
			logger.debug(list.size()+" external sources in "+rootElement.getName()+" "+rootFile.getAbsolutePath());
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next();
				String source =childElement.getAttribute("source").getValue();
				File childFile = new File(rootFile.getParentFile(),source);
				OfxExternalMerger em = new OfxExternalMerger(childFile);
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
		
		String fName = "resources/data/xml/preprocessor/exmerge/chapter-1.xml";
		if(args.length == 1 ){fName = args[0];}
		
		File f = new File(fName);
		OfxExternalMerger exMerger = new OfxExternalMerger(f);
		Document doc = exMerger.mergeToDoc();
		JDomUtil.debug(doc);
	}
}