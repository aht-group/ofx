package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.*;
import org.openfuxml.factory.xml.ofx.content.XmlCommentFactory;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SectionAndTitleProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Section build()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(create());
		return xml;
	}

	public static Section buildWithComment()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(create());
		xml.getContent().add(createComment());
		return xml;
	}
	
	public static Title create(){return create(4);}
	private static Title create(int words){return XmlTitleFactory.build(li.getWords(words));}

	private static Comment createComment()
	{
		Comment c = XmlCommentFactory.build();
		Raw r = new Raw(); r.setValue("This is a comment!");
		c.getRaw().add(r);
		return c;

	}
}