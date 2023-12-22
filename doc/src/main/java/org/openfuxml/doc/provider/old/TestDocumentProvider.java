package org.openfuxml.doc.provider.old;

import org.openfuxml.controller.provider.list.ListProvider;
import org.openfuxml.controller.provider.table.TableProvider;
import org.openfuxml.controller.provider.text.EmphasisProvider;
import org.openfuxml.controller.provider.text.ParagraphProvider;
import org.openfuxml.controller.provider.text.SectionProvider;
import org.openfuxml.controller.provider.text.TitleProvider;
import org.openfuxml.doc.provider.media.ImageProvider;
import org.openfuxml.factory.xml.ofx.content.structure.XmlDocumentFactory;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDocumentProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestDocumentProvider.class);
	
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
		s1.getContent().add(ImageProvider.figure("IMAGE"));
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
	
	public static Document buildComplexWithEmphasis()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();

		s1.getContent().add(EmphasisProvider.italic());
		s1.getContent().add(EmphasisProvider.bold());
		s1.getContent().add(EmphasisProvider.underline());
		xml.getContent().getContent().add(s1);
		
		return xml;
	}
	
	public static Document buildComplexALL()
	{
		Document xml = XmlDocumentFactory.withContent();
		Section s1 = SectionProvider.build();
		s1.getContent().add(ParagraphProvider.create(100));
		s1.getContent().add(ListProvider.build(true));
		s1.getContent().add(ListProvider.build(false));
		s1.getContent().add(MarginaliaProvider.inParagraph());
		s1.getContent().add(ParagraphProvider.create(20));
		s1.getContent().add(ReferenceProvider.create());
		s1.getContent().add(SectionProvider.buildWithMultiLevels());
		s1.getContent().add(TableProvider.buildWithoutSpecification());
		s1.getContent().add(TitleProvider.create());
		s1.getContent().add(EmphasisProvider.italic());

		xml.getContent().getContent().add(s1);
		
		return xml;
	}
}