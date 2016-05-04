package org.openfuxml.renderer.word;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.wiki.AbstractTestWikiRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractTestWordRenderer {

	final static Logger logger = LoggerFactory.getLogger(AbstractTestWikiRenderer.class);

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
		Section s = new Section();
		s.getContent().add(XmlTitleFactory.build("Introduction"));
		s.getContent().add(buildParagraph());
		return s;
	}

}
