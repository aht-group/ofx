package org.openfuxml.util.provider;

import org.openfuxml.content.list.List;
import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.list.XmlListFactory2;
import org.openfuxml.factory.xml.ofx.content.structure.XmlDocumentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;

public class DemoContentProvider
{	
	final static Logger logger = LoggerFactory.getLogger(DemoContentProvider.class);
	
	public static LoremIpsum li = new LoremIpsum();
	
	public static Document buildComplexALL()
	{
//		Section s1 = SectionProvider.build();
//		s1.getContent().add(ParagraphProvider.create(100));
//		s1.getContent().add(ListProvider.build(true));
//		s1.getContent().add(ListProvider.build(false));
//		s1.getContent().add(MarginaliaProvider.inParagraph());
//		s1.getContent().add(ParagraphProvider.create(20));
//		s1.getContent().add(ReferenceProvider.create());
//		s1.getContent().add(SectionProvider.buildWithMultiLevels());
//		s1.getContent().add(TableProvider.buildWithoutSpecification());
//		s1.getContent().add(TitleProvider.create());
//		s1.getContent().add(EmphasisProvider.italic());

		Document xml = XmlDocumentFactory.withContent();
		xml.getContent().getContent().add(c1());
		xml.getContent().getContent().add(c2());
		
		return xml;
	}
	
	private static Section c1()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("1");
		xml.getContent().add(XmlTitleFactory.build("Chapter 1: OFX Demo Document"));
		xml.getContent().add(XmlParagraphFactory.text("We will have two simple paragraphs in this section"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(25)));
		return xml;
	}
	
	private static Section c2()
	{	
		Section xml = XmlSectionFactory.build();
		xml.setId("2");
		xml.getContent().add(XmlTitleFactory.build("Chapter 2: Lists"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(25)));
		xml.getContent().add(ListProvider.unordered());
		return xml;
	}
}