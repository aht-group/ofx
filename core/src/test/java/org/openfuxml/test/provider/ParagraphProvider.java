package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.factory.xml.editorial.XmlIndexFactory;
import org.openfuxml.renderer.latex.structure.TestLatexParagraphRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphProvider extends AbstractElementProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TestLatexParagraphRenderer.class);
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(li.getWords(words));
    	return p;
	}
	
	public static Paragraph paragraphWithIndex()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(li.getWords(1)+" ");
		p.getContent().add(XmlIndexFactory.build("testIndex"));
		p.getContent().add(" "+li.getWords(3));
		return p;
	}
}