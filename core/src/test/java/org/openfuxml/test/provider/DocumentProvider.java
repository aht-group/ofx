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
		Document xml = XmlDocumentFactory.withContent();

		xml.getContent().getContent().add(SectionProvider.build());
		xml.getContent().getContent().add(SectionProvider.build());

		return xml;
	}

	public static Document buildWithSubcontent()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(SectionProvider.buildWithComment());
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplex()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		
		s1.getContent().add(SectionProvider.buildWithComment());
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplexWithImage()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(ImageProvider.createImageOnly("!!!!IMAGE!!!!Titel-pleeaaasssseeeee!!!!"));
		s1.getContent().add(SectionProvider.buildWithComment());
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplexWithTableSimple()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(SectionProvider.buildWithComment());
		s1.getContent().add(TableProvider.createSimple(2,2));
		xml.getContent().getContent().add(s1);

		return xml;
	}

	public static Document buildComplexWithTable()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(SectionProvider.buildWithComment());
		s1.getContent().add(TableProvider.create(4, 3));
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplexWith2Table()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(SectionProvider.buildWithComment());
		s1.getContent().add(TableProvider.create(3, 10));
		s1.getContent().add(TableProvider.create(4, 3));
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplexWith3Table()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(SectionProvider.buildWithComment());
		s1.getContent().add(TableProvider.create(3, 10));
		s1.getContent().add(TableProvider.create(4, 3));
		s1.getContent().add(TableProvider.create(2, 5));
		xml.getContent().getContent().add(s1);

		return xml;
	}
	
	public static Document buildComplexWith3TableWithParagraphInBetween()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		Section s2 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(SectionProvider.buildWithComment());
		s1.getContent().add(TableProvider.create(3, 8));
		s1.getContent().add(TableProvider.create(4, 3));
		
		s2.getContent().add(ParagraphProvider.create(80));
		s2.getContent().add(TableProvider.create(2, 5));
		xml.getContent().getContent().add(s1);
		xml.getContent().getContent().add(s2);
		
		return xml;
	}
	
	public static Document buildComplexWithList()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(ListProvider.build(true));
		xml.getContent().getContent().add(s1);
		
		return xml;
	}
	
}