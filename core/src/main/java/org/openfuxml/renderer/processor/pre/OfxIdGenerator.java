package org.openfuxml.renderer.processor.pre;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.output.Format;
import org.jdom.xpath.XPath;
import org.openfuxml.exception.OfxInternalProcessingException;

public class OfxIdGenerator
{
	static Log logger = LogFactory.getLog(OfxIdGenerator.class);
	
	private Document doc;
	private XPath xpath;
	private int autoId;
	
	public OfxIdGenerator()
	{
		autoId = 1;
		try
		{
			
			xpath = XPath.newInstance("//ofx:section");
			xpath.addNamespace(Namespace.getNamespace("ofx", "http://www.openfuxml.org"));
			xpath.addNamespace(Namespace.getNamespace("wiki", "http://www.openfuxml.org/wiki"));
			
//			List<?> list = xpath.selectNodes(doc.getRootElement());
//			logger.debug(list.size()+" hits");
			
		}
		catch (JDOMException e) {logger.error(e);}
	}
	
	public void createIds(File srcFile, File dstFile) throws OfxInternalProcessingException
	{
		if(srcFile==null){throw new OfxInternalProcessingException("FileNoteFound: "+srcFile.getAbsolutePath());}
		doc = JDomUtil.load(srcFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+srcFile.getAbsolutePath());}
		
		try
		{
			idCreator();
			JDomUtil.save(doc, dstFile, Format.getRawFormat());
		}
		catch (JDOMException e)
		{
			logger.error(e);
		}
	}
	
	private void idCreator() throws JDOMException
	{
		List<?> list = xpath.selectNodes(doc.getRootElement());
		logger.debug(list.size()+" sections for idTagging");
		
		for (Iterator<?> iter = list.iterator(); iter.hasNext();)
		{
			Element e = (Element) iter.next();
			if(e.getAttribute("id")==null)
			{
				e.setAttribute("id", "autoId"+autoId);autoId++;
			}
		}
	}
	
}