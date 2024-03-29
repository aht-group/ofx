package org.openfuxml.util.reference;

import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Content;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
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
		
		content.getContent().add(introduction());
		
		return content;
	}
	
	private static Section introduction()
	{
		Section introduction = new Section();
		introduction.getContent().add(XmlTitleFactory.build("Introduction"));
		introduction.getContent().add(XmlParagraphFactory.lang("Test 123"));
		
		Section credits = new Section();
		credits.getContent().add(XmlTitleFactory.build("Credits"));
		credits.getContent().add(XmlParagraphFactory.lang("Test 456"));
		introduction.getContent().add(credits);
		
		Section structure = new Section();
		structure.getContent().add(XmlTitleFactory.build("Structure"));
		structure.getContent().add(XmlParagraphFactory.lang("Test 789"));
		introduction.getContent().add(structure);
		
		return introduction;
	}
}