package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
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

		xml.getContent().getContent().add(SectionProvider.build());
		xml.getContent().getContent().add(SectionProvider.build());

		return xml;
	}

	public static Document buildWithSubcontent()
	{
		Document xml = XmlDocumentFactory.build();
		Section s1 = SectionProvider.build();
		s1.getContent().add(SectionProvider.buildWithComment());
		xml.getContent().getContent().add(s1);

		return xml;
	}
}