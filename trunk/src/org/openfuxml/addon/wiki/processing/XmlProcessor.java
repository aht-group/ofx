package org.openfuxml.addon.wiki.processing;

import net.sf.exlp.util.xml.JDomUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jdom.DocType;
import org.jdom.Document;

public class XmlProcessor
{
	static Logger logger = Logger.getLogger(XmlProcessor.class);
	private Document doc;
	
	public XmlProcessor(Configuration config)
	{
		
	}
	
	public Document process(String xHtml)
	{
		doc = JDomUtil.txtToDoc(xHtml);
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