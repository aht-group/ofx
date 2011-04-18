package org.openfuxml.addon.wiki.processing;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.JDOMException;

@Deprecated
public class XmlProcessor
{
	static Log logger = LogFactory.getLog(XmlProcessor.class);
	private Document doc;
	
	public XmlProcessor()
	{
		
	}
	
	public Document process(String xHtml)
	{
		try {
			doc = JDomUtil.txtToDoc(xHtml);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDocType(1);
		return doc;
	}
	
	private void setDocType(int dtdLevel)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<dtdLevel;i++)
		{
			sb.append("../");
		}
		DocType docType = new DocType("abschnitt");
		docType.setSystemID(sb.toString()+"system/dtd/fernuni01.dtd");
		doc.setDocType(docType);
	}
}