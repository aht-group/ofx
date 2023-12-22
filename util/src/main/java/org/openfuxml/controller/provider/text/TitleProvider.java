package org.openfuxml.controller.provider.text;

import org.openfuxml.controller.provider.DemoContentProvider;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.model.xml.core.ofx.Title;
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