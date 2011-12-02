package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.io.RelativePathFactory;
import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;
import org.openfuxml.content.ofx.Ofxdoc;
import org.openfuxml.exception.OfxInternalProcessingException;

public class OfxExternalMerger
{
	static Log logger = LogFactory.getLog(OfxExternalMerger.class);
	
	private RelativePathFactory rpf;
	private File rootFile;
	private Document doc;
	
	private XPath xpath;
	
	public OfxExternalMerger()
	{
		rpf = new RelativePathFactory(RelativePathFactory.PathSeparator.CURRENT);
		try
		{
			xpath = XPath.newInstance("//*[@external='true']");
			xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));		
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	private void loadRoot(File rootFile) throws OfxInternalProcessingException
	{
		this.rootFile=rootFile;
		doc = JDomUtil.load(rootFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+rootFile.getAbsolutePath());}
		logger.trace("Loaded: "+rootFile.getAbsolutePath());
	}
	
	public Ofxdoc mergeToOfxDoc(File rootFile) throws OfxInternalProcessingException
	{
		Document doc = mergeToDoc(rootFile);
		Ofxdoc ofxDoc = (Ofxdoc)JDomUtil.toJaxb(doc, Ofxdoc.class);
		return ofxDoc;
	}
	
	public Document mergeToDoc(File rootFile) throws OfxInternalProcessingException
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
				OfxExternalMerger em = new OfxExternalMerger();
				Element eEx = em.getExternal(childFile);
				eEx.detach();
				int index = childElement.getParentElement().indexOf(childElement);
				childElement.getParentElement().setContent(index, eEx);
				childElement.detach();
			}
		}
		catch (JDOMException e) {logger.error(e);}
		return rootElement;
	}
}