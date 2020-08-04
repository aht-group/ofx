package org.openfuxml.test.provider;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.factory.xml.ofx.OfxReferenceFactory;
import org.openfuxml.util.provider.DemoContentProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReferenceProvider
{
	final static Logger logger = LoggerFactory.getLogger(ReferenceProvider.class);

	public static Paragraph create()
	{
		Paragraph p = new Paragraph();
		p.getContent().add(DemoContentProvider.li.getWords(1)+" ");
		logger.info(OfxReferenceFactory.build("www.test.de").getTarget());
		p.getContent().add(OfxReferenceFactory.build("www.test.de"));
		p.getContent().add(" "+DemoContentProvider.li.getWords(3));
		return p;
	}
}