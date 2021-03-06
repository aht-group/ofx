package org.openfuxml.doc.provider.old;

import org.openfuxml.content.ofx.Marginalia;
import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.doc.provider.text.ParagraphProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarginaliaProvider
{	
	final static Logger logger = LoggerFactory.getLogger(MarginaliaProvider.class);
	
	public static Marginalia withParagraph()
	{
		Marginalia marginalia = new Marginalia();
		marginalia.getContent().add(ParagraphProvider.create());
		return marginalia;
	}
	
	public static Paragraph inParagraph()
	{
    	Paragraph paragraph = ParagraphProvider.create(15);
    	paragraph.getContent().add(0, MarginaliaProvider.withParagraph());
    	return paragraph;
	}
}