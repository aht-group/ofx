package org.openfuxml.util.reference;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxReferenceDocumentBuilder
{
	final static Logger logger = LoggerFactory.getLogger(OfxReferenceDocumentBuilder.class);
	
	public static Document ofxDocument()
	{
		Document doc = new Document();
		doc.setContent(createContent());
		return doc;
	}
	
	private static Content createContent()
	{
		Content content = new Content();
		
		return content;
	}
}
