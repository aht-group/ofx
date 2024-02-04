package org.openfuxml.processor.pre;

import java.io.File;
import java.util.List;

import org.exlp.util.jx.JaxbUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.output.Format;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.xml.OfxNsPrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.util.xml.JDomUtil;

public class OfxIdGenerator
{
	final static Logger logger = LoggerFactory.getLogger(OfxIdGenerator.class);
	
	private Document doc;
	private XPathExpression<Element> xpath2;
	private int autoId;
	
	public OfxIdGenerator()
	{
		autoId = 1;
		
		xpath2 = XPathFactory.instance().compile("//ofx:section", Filters.element(), null, OfxNsPrefixMapper.nsOfx);
	}
	
	public org.openfuxml.model.xml.core.ofx.Document generate(org.openfuxml.model.xml.core.ofx.Document ofxDoc) throws JDOMException
	{
		doc = JaxbUtil.toDocument(ofxDoc);
		
		idCreator();
		
		return JDomUtil.toJaxb(doc,org.openfuxml.model.xml.core.ofx.Document.class);
		
	}
	
	public void createIds(File srcFile, File dstFile) throws OfxInternalProcessingException
	{
		if(srcFile==null){throw new OfxInternalProcessingException("srcFile is null");}
		doc = JDomUtil.load(srcFile);
		if(doc==null){throw new OfxInternalProcessingException("FileNoteFound: "+srcFile.getAbsolutePath());}
		
		try
		{
			idCreator();
			JDomUtil.save(doc, dstFile, Format.getRawFormat());
		}
		catch (JDOMException e)
		{
			logger.error("",e);
		}
	}
	
	private void idCreator() throws JDOMException
	{
		List<Element> list2 = xpath2.evaluate(doc.getRootElement());
		logger.debug(list2.size()+" sections for idTagging");

		for(Element e : list2)
		{
			if(e.getAttribute("id")==null)
			{
				e.setAttribute("id", "autoId"+autoId);autoId++;
			}
		}
	}
}