package org.openfuxml.util.reference;

import org.openfuxml.content.ofx.Content;
import org.openfuxml.content.ofx.Ofxdoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OfxReferenceDocumentBuilder
{
	final static Logger logger = LoggerFactory.getLogger(OfxReferenceDocumentBuilder.class);
	
	public static Ofxdoc ofxDocument()
	{
		Ofxdoc doc = new Ofxdoc();
		doc.setContent(createContent());
		return doc;
	}
	
	private static Content createContent()
	{
		Content content = new Content();
		
		return content;
	}
}
