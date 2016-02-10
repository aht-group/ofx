package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.factory.xml.ofx.content.structure.XmlDocumentFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Document build()
	{
		Document xml = XmlDocumentFactory.build();
		
		xml.getContent().getContent().add(SectionAndTitleProvider.withTitleContent());
		xml.getContent().getContent().add(SectionAndTitleProvider.withTitleContent());
		
		return xml;
	}
}