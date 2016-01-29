package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.renderer.latex.content.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Section build()
	{
		Section xml = XmlSectionFactory.build();
		xml.setId("myId");
		xml.getContent().add(create());
		return xml;
	}
	
	public static Title create(){return create(4);}
	private static Title create(int words)
	{
    	Title xml = XmlTitleFactory.build(li.getWords(words));
    	return xml;
	}
}