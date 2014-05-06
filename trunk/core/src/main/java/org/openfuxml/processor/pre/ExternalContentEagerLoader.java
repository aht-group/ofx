package org.openfuxml.processor.pre;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JDomUtil;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPath;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExternalContentEagerLoader
{
	final static Logger logger = LoggerFactory.getLogger(ExternalContentEagerLoader.class);
	
	private RelativePathFactory rpf;
	private File rootFile;
	private org.jdom2.Document doc;
	private XPathFactory xpFactory;
	
	private XPath xpath;
	
	public ExternalContentEagerLoader()
	{
		rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.CURRENT);
		try
		{
			xpath = XPath.newInstance("//*[@external='true']");
			xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));		
		}
		catch (JDOMException e) {logger.error("",e);}
		xpFactory = XPathFactory.instance();
	}
	
	protected XPathExpression<Element> build()
	{
		Namespace ns = Namespace.getNamespace("ofx", "http://www.openfuxml.org");
		
		XPathExpression<Element> xpe= xpFactory.compile("//*[@external]", Filters.element());
	
		return xpe;
	}
	
	private void loadRoot(File rootFile) throws OfxInternalProcessingException
	{
		this.rootFile=rootFile;
		doc = JDomUtil.load(rootFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+rootFile.getAbsolutePath());}
		logger.trace("Loaded: "+rootFile.getAbsolutePath());
	}
	
	public Document mergeToOfxDoc(File rootFile) throws OfxInternalProcessingException
	{
		org.jdom2.Document doc = mergeToDoc(rootFile);
		Document ofxDoc = (Document)JDomUtil.toJaxb(doc, Document.class);
		return ofxDoc;
	}
	
	public org.jdom2.Document mergeToDoc(File rootFile) throws OfxInternalProcessingException
	{
		loadRoot(rootFile);
		
		Element rootElement = doc.getRootElement();
		Element result = mergeRecursive(rootElement);
		result.detach();
		doc.setRootElement(result);
		return doc;
	}
	
	public Element getExternal(File rootFile) throws OfxInternalProcessingException
	{
		loadRoot(rootFile);
		Element rootElement = doc.getRootElement();
		
		return mergeRecursive(rootElement);
	}
	
	private Element mergeRecursive(Element rootElement) throws OfxInternalProcessingException
	{
		try
		{
			List<?> list = xpath.selectNodes(rootElement);
			logger.debug(list.size()+" external sources in "+rootElement.getName()+" in "+rootFile.getAbsolutePath());
			
			for (Iterator<?> iter = list.iterator(); iter.hasNext();)
			{
				Element childElement = (Element) iter.next();
				String source =childElement.getAttribute("source").getValue();
				File childFile = new File(rootFile.getParentFile(),source);
				logger.trace("Found external in "+rpf.relativate(rootFile.getParentFile(), childFile));
				ExternalContentEagerLoader em = new ExternalContentEagerLoader();
				Element eEx = em.getExternal(childFile);
				eEx.detach();
				int index = childElement.getParentElement().indexOf(childElement);
				childElement.getParentElement().setContent(index, eEx);
				childElement.detach();
			}
		}
		catch (JDOMException e) {logger.error("",e);}
		return rootElement;
	}
}