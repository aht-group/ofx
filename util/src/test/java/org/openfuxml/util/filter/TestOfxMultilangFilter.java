package org.openfuxml.util.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxInternalProcessingException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Section;
import org.openfuxml.test.AbstractOfxUtilTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestOfxMultilangFilter extends AbstractOfxUtilTest
{	
	final static Logger logger = LoggerFactory.getLogger(TestOfxMultilangFilter.class);

	private Section sectionTitle,sectionSection,sectionParagraph;
	
	@BeforeEach
	public void init()
	{
		sectionTitle = XmlSectionFactory.build();
		sectionTitle.getContent().add(XmlTitleFactory.build("de","test"));
		sectionTitle.getContent().add(XmlTitleFactory.build("en","test"));
		
		sectionSection = XmlSectionFactory.build();
		sectionSection.getContent().add(XmlSectionFactory.build("de"));
		sectionSection.getContent().add(XmlSectionFactory.build("en"));
		
		sectionParagraph = XmlSectionFactory.build();
		sectionParagraph.getContent().add(XmlParagraphFactory.lang("de"));
		sectionParagraph.getContent().add(XmlParagraphFactory.lang("en"));
	}
	
	@Test
	public void sectionWithTitles() throws OfxAuthoringException, OfxInternalProcessingException
	{
		Assertions.assertEquals(2, sectionTitle.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionTitle = mlf.filterLang(sectionTitle);
		Assertions.assertEquals(1, sectionTitle.getContent().size());
	}
	
	@Test
	public void sectionWithSections()
	{
		Assertions.assertEquals(2, sectionSection.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionSection = mlf.filterLang(sectionSection);
		Assertions.assertEquals(1, sectionSection.getContent().size());
	}
	
	@Test
	public void sectionWithParagraphs()
	{
		Assertions.assertEquals(2, sectionParagraph.getContent().size());
		OfxLangFilter mlf = new OfxLangFilter("de");
		sectionParagraph = mlf.filterLang(sectionParagraph);
		Assertions.assertEquals(1, sectionParagraph.getContent().size());
	}
}