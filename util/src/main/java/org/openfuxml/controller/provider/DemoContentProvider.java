package org.openfuxml.controller.provider;

import org.openfuxml.controller.provider.list.ListProvider;
import org.openfuxml.controller.provider.table.TableProvider;
import org.openfuxml.factory.xml.ofx.content.structure.XmlDocumentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Document;
import org.openfuxml.model.xml.core.ofx.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.svenjacobs.loremipsum.LoremIpsum;

public class DemoContentProvider
{	
	final static Logger logger = LoggerFactory.getLogger(DemoContentProvider.class);
	
	public static LoremIpsum li = new LoremIpsum();
	
	private boolean withIds; public DemoContentProvider withIds(boolean withIds) {this.withIds = withIds; return this;}
	public static DemoContentProvider instance() {return new DemoContentProvider();}
	public DemoContentProvider()
	{
		withIds = true;
	}
	
	public Document build()
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
		xml.getContent().getContent().add(c3());
		xml.getContent().getContent().add(c4());
		return xml;
	}
	
	private Section c1()
	{
		Section xml = XmlSectionFactory.build();
		if(withIds) {xml.setId("1");}
		xml.getContent().add(XmlTitleFactory.build("Chapter 1: Paragrahs"));
		xml.getContent().add(XmlParagraphFactory.text("We will have two simple paragraphs in this section"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(25)));
		return xml;
	}
	
	private Section c2()
	{	
		Section xml = XmlSectionFactory.build();
		if(withIds) {xml.setId("2");}
		xml.getContent().add(XmlTitleFactory.build("Chapter 2: Lists"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(25)));
		xml.getContent().add(ListProvider.unordered());
		return xml;
	}
	
	private Section c3()
	{	
		Section xml = XmlSectionFactory.build();
		if(withIds) {xml.setId("3");}
		xml.getContent().add(XmlTitleFactory.build("Chapter 3: Sections with Sub-Sections"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(2)));
		
		Section x1 = XmlSectionFactory.build();
		if(withIds) {x1.setId("3.1");}
		x1.getContent().add(XmlTitleFactory.build("Section 3.1: Section A"));
		x1.getContent().add(XmlParagraphFactory.text(li.getWords(2)));
		
		
		Section x11 = XmlSectionFactory.build();
		if(withIds) {x11.setId("3.1.1");}
		x11.getContent().add(XmlTitleFactory.build("Section 3.1.1: Section A.1"));
		x11.getContent().add(XmlParagraphFactory.text(li.getWords(2)));
		x1.getContent().add(x11);
		xml.getContent().add(x1);
		
		Section x2 = XmlSectionFactory.build();
		if(withIds) {x2.setId("3.2");} 
		x2.getContent().add(XmlTitleFactory.build("Section 3.2: Section B"));
		x2.getContent().add(XmlParagraphFactory.text(li.getWords(2)));
		xml.getContent().add(x2);
		
		return xml;
	}
	
	private Section c4()
	{	
		Section xml = XmlSectionFactory.build();
		if(withIds) {xml.setId("3");}
		xml.getContent().add(XmlTitleFactory.build("Chapter 4: Table"));
		xml.getContent().add(XmlParagraphFactory.text(li.getWords(2)));
		xml.getContent().add(TableProvider.buildWithoutSpecification());
		
		return xml;
	}
}