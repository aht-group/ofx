package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.test.provider.SectionProvider;
import org.openfuxml.test.provider.TableProvider;

public class AbstractTestWordRenderer {

	/**
	 * Creates a test paragraph as XML
	 * 
	 * @return Ofx Paragraph
	 */
	public Paragraph buildParagraph() {
		Paragraph p = XmlParagraphFactory.build("Test 123");
		p.getContent().add("Test Text CV");
		return p;
	}

	/**
	 * Creates a test Sections with paragraph
	 * 
	 * @return Ofx Section
	 */
	public Section buildSection() {
		Section s = SectionProvider.buildWithMultiLevels();
		s.getContent().add(TableProvider.create());
		s.getContent().add(TableProvider.create());
		return s;
	}
	
	/**
	 * Creates a test title
	 * @return Ofx Title
	 */
	public Title buildTitle() {
		Title t = new Title();
		t.getContent().add(XmlTitleFactory.build("Test Title"));
		return t;
	}
	
	/**
	 * Creates a test table
	 * @return
	 */
	public Table buildTable(){
		Table t = TableProvider.create();
		return t;
	}

}
