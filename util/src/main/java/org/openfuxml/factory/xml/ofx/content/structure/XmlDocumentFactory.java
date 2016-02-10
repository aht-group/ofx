package org.openfuxml.factory.xml.ofx.content.structure;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Document;

public class XmlDocumentFactory
{
	public static Document withContent()
	{
		Document xml = build();
		xml.setContent(new Content());
		return xml;
	}	
	
	public static Document build()
	{
		return new Document();
	}	
}