package org.openfuxml.doc.provider.text;

import org.openfuxml.content.ofx.Title;
import org.openfuxml.doc.provider.DemoContentProvider;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TitleProvider
{	
	final static Logger logger = LoggerFactory.getLogger(TitleProvider.class);
	
	public static Title create(){return create(4);}
	private static Title create(int words)
	{
		return XmlTitleFactory.build(DemoContentProvider.li.getWords(words));
	}
}