package org.openfuxml.doc.provider.text;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.factory.xml.editorial.XmlIndexFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParagraphProvider
{	
	final static Logger logger = LoggerFactory.getLogger(ParagraphProvider.class);
	
	public static Paragraph create(){return create(10);}
	public static Paragraph create(int words)
	{
    	Paragraph p = new Paragraph();
    	p.getContent().add(DemoContentProvider.li.getWords(words));
    	return p;
	}
	
	public static Paragraph paragraphWithIndex()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(DemoContentProvider.li.getWords(1)+" ");
		p.getContent().add(XmlIndexFactory.build("testIndex"));
		p.getContent().add(" "+DemoContentProvider.li.getWords(3));
		return p;
	}
}