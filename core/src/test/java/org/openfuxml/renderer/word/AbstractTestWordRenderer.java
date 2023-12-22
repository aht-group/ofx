package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Document;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.controller.provider.table.TableProvider;
import org.openfuxml.controller.provider.text.SectionProvider;
import org.openfuxml.doc.provider.old.TestDocumentProvider;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.table.Table;

public class AbstractTestWordRenderer {

	/**
	 * Creates a test paragraph as XML
	 * 
	 * @return Ofx Paragraph
	 */
	@Deprecated
	public Paragraph buildParagraph() {
		Paragraph p = XmlParagraphFactory.lang("Test 123");
		p.getContent().add("Test Text CV");
		return p;
	}

	/**
	 * Creates a test Sections with paragraph
	 * 
	 * @return Ofx Section
	 */
	@Deprecated
	public Section buildSection() {
		Section s = SectionProvider.buildWithMultiLevels();
//		s.getContent().add(buildTitle());
		s.getContent().add(TableProvider.create());
		s.getContent().add(TableProvider.create());
		return s;
	}
	
	@Deprecated
	public Document buildDocument(){
		Document d = TestDocumentProvider.build();
		d.getContent().getContent().add(buildSection());
		d.getContent().getContent().add(buildTable());
		return d;
	}
	
	/**
	 * Creates a test title
	 * @return Ofx Title
	 */
	@Deprecated
	public Title buildTitle() {
		Title t = new Title();
		t.getContent().add(XmlTitleFactory.build("Test Title 1"));
		t.getContent().add(XmlTitleFactory.build("Test Title 2"));
		t.getContent().add(XmlTitleFactory.build("Test Title 3"));
		t.getContent().add(XmlTitleFactory.build("Test Title 4"));
		return t;
	}
	
	/**
	 * Creates a test table
	 * @return
	 */
	@Deprecated
	public Table buildTable(){
		Table t = TableProvider.create();
		return t;
	}

}
