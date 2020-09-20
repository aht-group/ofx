package org.openfuxml.doc.provider.text;

import org.openfuxml.content.ofx.Comment;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Raw;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionProvider
{	
	final static Logger logger = LoggerFactory.getLogger(SectionProvider.class);
	
	public static Section build()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(TitleProvider.create());
		return xml;
	}

	public static Section buildWithComment()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(TitleProvider.create());
		xml.getContent().add(createComment());
		return xml;
	}
	
	public static Section buildWithMultiLevels()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(TitleProvider.create());
		
		Paragraph p = XmlParagraphFactory.build();
        p.getContent().add("testParagraph with lè test");
        p.getContent().add(EmphasisProvider.italicBold());
        xml.getContent().add(p);
		
		return xml;
	}

	private static Comment createComment()
	{
		Comment c = XmlCommentFactory.build();
		Raw r = new Raw(); r.setValue("This is a comment!");
		c.getRaw().add(r);
		return c;
	}
}